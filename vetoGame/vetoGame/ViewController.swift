//
//  ViewController.swift
//  vetoGame
//
//  Created by Cristina on 3/23/15.
//  Copyright (c) 2015 CristinaChu. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    @IBOutlet weak var logout: UIButton!
    @IBOutlet weak var tableView: UITableView!
    
    var userID : String!
    var fbSession : FBSession!
    var total : Int = 0
    var games = [NSArray]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        getCurrentGames()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func logginOut(sender: AnyObject) {
        fbSession.closeAndClearTokenInformation()
        self.performSegueWithIdentifier("logout", sender: self)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if (segue.identifier == "showProfile") {
            var destViewController : profileViewController = segue.destinationViewController as! profileViewController
            
            destViewController.fbSession = FBSession.activeSession()
            destViewController.userID = self.userID
        }
        
        if (segue.identifier == "createGame") {
            var destViewController : createGameViewController = segue.destinationViewController as! createGameViewController
            
            destViewController.userID = self.userID
        }
        
        if (segue.identifier == "pastGames"){
            var destViewController : pastGamesTableViewController = segue.destinationViewController as! pastGamesTableViewController
            
            destViewController.userID = self.userID
        }
        
        if (segue.identifier == "logout"){
            //var destViewController : facebookLoginViewController = segue.destinationViewController as facebookLoginViewController
            
            //TODO Restart App
        }
    }
    
    func getCurrentGames() {
        //var url : String = "http://173.236.253.103:28080/game_data/get_current_games/"+self.userID
        var url : String = "http://173.236.253.103:28080/game_data/get_current_games/ABC"
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
                println("No current games")
            }
        })
    }


}

