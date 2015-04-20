//
//  pastGamesTableViewController.swift
//  vetoGame
//
//  Created by Cristina on 4/19/15.
//  Copyright (c) 2015 CristinaChu. All rights reserved.
//

import Foundation
import UIKit

class pastGamesTableViewController : UITableViewController {

    var userID : String!
    var candies = [String]()
    var games = [NSArray]()
        //game = [name, id, eventType, suggestionTTL, radius, center, currentSuggestionName, eventTime, timeEnding]
    var total : Int = 0
    
    override func viewDidLoad() {
        //getPastGames()
        
        super.viewDidLoad()
        
        //getPastGames()
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.total
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = self.tableView.dequeueReusableCellWithIdentifier("Cell", forIndexPath: indexPath) as! gameCell

        var game : NSArray
        game = games[indexPath.row]
        
        //cell.gameName.text = game[0] as! String
        //cell.currentSuggestion.text = game[6] as! String
        
        cell.gameName.text = "Hi"
        cell.currentSuggestion.text = "This"
        
        println("Cell stuff")
        
        return cell
    }
    
    func getPastGames() {
        //var url : String = "http://173.236.253.103:28080/game_data/get_past_games/"+self.userID
        var url : String = "http://173.236.253.103:28080/game_data/get_past_games/ABC"
        var request : NSMutableURLRequest = NSMutableURLRequest()
        
        request.URL = NSURL(string: url)
        request.HTTPMethod = "GET"
        
        NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue(), completionHandler:{ (response:NSURLResponse!, data: NSData!, error: NSError!) -> Void in
            var error: AutoreleasingUnsafeMutablePointer<NSError?> = nil
            
            let jsonResult = NSJSONSerialization.JSONObjectWithData(data, options:NSJSONReadingOptions.MutableContainers, error: error)
            
            var totalGames : Int = jsonResult!.count as Int
            self.total = totalGames
            
            if (jsonResult != nil && self.total>0) {
                // process jsonResult - assigning values to labels
                //TODO --> put all the stuff in an array
                
                //Going through all the games
                for (var i=0; i<2; i++) {
                    //Getting each game through index
                    var result = jsonResult!.objectAtIndex(i)
                    
                    //formatting dates (eventTime, timeEnding)
                    var dateFormatter = NSDateFormatter()
                    dateFormatter.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'"
                    
                    var eventTime = dateFormatter.dateFromString(result.objectForKey("eventTime") as! String) as NSDate!
                    var timeEnding = dateFormatter.dateFromString(result.objectForKey("timeEnding") as! String) as NSDate!
                    
                    //Getting all the information from the jsonResult
                    var name = result.objectForKey("name") as! String
                    var id = result.objectForKey("id") as! Int
                    var eventType = result.objectForKey("eventType") as! String
                    var suggestionTTL = result.objectForKey("suggestionTTL") as! Int
                    var radius = result.objectForKey("radius") as! Int
                    var currentSuggestionName = (result.objectForKey("currentSuggestion"))!.objectForKey("name") as! String
                    
                    //needs to be change to location
                    var center = result.objectForKey("center") as! String
                    
                    var newGame = [name, id, eventType, suggestionTTL, radius, center, currentSuggestionName, eventTime, timeEnding]
                    
                    self.games.append(newGame)
                }
                
            } else {
                // no current games!
                println("No past games")
            }
            println(self.games)
        })
    }
    
}