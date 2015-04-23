//
//  createGameViewController.swift
//  vetoGame
//
//  Created by Cristina on 3/23/15.
//  Copyright (c) 2015 CristinaChu. All rights reserved.
//

import Foundation
import UIKit

class createGameViewController : UIViewController {
    
    let currentDate = NSDate()
    var userID: String!
    var radius : Int!
    var friends : NSArray!
    
    //used for the POST request
    var eventTimePOST : String!
    var gameTimeEndPOST : String!
    
    //variables on the UI
    @IBOutlet weak var gameNameTextField: UITextField!
    @IBOutlet weak var categoryTextField: UITextField!
    @IBOutlet weak var selectPlayers: UIButton!
    @IBOutlet weak var eventDate: UITextField!
    @IBOutlet weak var gameEndTime: UITextField!
    @IBOutlet weak var initialSuggestionButton: UIButton!
    @IBOutlet weak var createGameButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @IBAction func selectEventDate(sender: UITextField) {
        var datePickerView  : UIDatePicker = UIDatePicker()
        
        datePickerView.datePickerMode = UIDatePickerMode.DateAndTime
        sender.inputView = datePickerView
        datePickerView.minimumDate = self.currentDate
        datePickerView.addTarget(self, action: Selector("handleDateStart:"), forControlEvents: UIControlEvents.ValueChanged)
        
        //TODO: make start date only possible for up to a certain amount of days (datePickerView.maximumDate)
    }
    
    
    @IBAction func selectGameEndTime(sender: UITextField) {
        var datePickerView  : UIDatePicker = UIDatePicker()
        
        datePickerView.datePickerMode = UIDatePickerMode.DateAndTime
        sender.inputView = datePickerView
        datePickerView.addTarget(self, action: Selector("handleDateEnd:"), forControlEvents: UIControlEvents.ValueChanged)
        
        //TODO: make ending date only possible for later dates and up to a certain amount of days
        datePickerView.minimumDate = self.currentDate
    }
    
    func handleDateStart(sender: UIDatePicker) {
        var timeFormatter = NSDateFormatter()
        timeFormatter.dateStyle = .LongStyle
        timeFormatter.timeStyle = .ShortStyle
        self.eventDate.text = timeFormatter.stringFromDate(sender.date)
        
        timeFormatter.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'"
        self.eventTimePOST = timeFormatter.stringFromDate(sender.date)
    }

    func handleDateEnd(sender: UIDatePicker) {
        var timeFormatter = NSDateFormatter()
        timeFormatter.dateStyle = .LongStyle
        timeFormatter.timeStyle = .ShortStyle
        gameEndTime.text = timeFormatter.stringFromDate(sender.date)
        
        timeFormatter.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'"
        self.gameTimeEndPOST = timeFormatter.stringFromDate(sender.date)
    }
    
    @IBAction func createGame(sender: AnyObject) {
        //TODO: Call database API to create new game
        //TODO: Create new game in the app
            //TODO: Make sure that all elements are valid/exist
        
        //Going back to the Home page
        self.navigationController?.popViewControllerAnimated(true)
    }
    
    
    override func touchesBegan(touches: Set<NSObject>, withEvent event: UIEvent) {
        view.endEditing(true)
        super.touchesBegan(touches as Set<NSObject>, withEvent: event)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if (segue.identifier == "initialSuggestion") {
            var tabBar : UITabBarController = segue.destinationViewController as! UITabBarController
    
            var destViewMap: initialSuggestionMapViewController = tabBar.viewControllers?.first as! initialSuggestionMapViewController
            var destViewList: initialSuggestionListViewController = tabBar.viewControllers?[1] as! initialSuggestionListViewController
            
            destViewList.eventType = self.categoryTextField.text
            
            //sending the mapView to the listView
            destViewList.mapView = destViewMap
        }
    }
    
    //Sending all info to database
    func createGameDB() {
        var url : String = "http://173.236.253.103:28080/game_data/create"
        var request : NSMutableURLRequest = NSMutableURLRequest()
        request.URL = NSURL(string: url)
        request.HTTPMethod = "POST"
        
        
        //Creating the post parameters
        
        //missing suggestion_ttl, center, radius
        var postParams = "user_id="+self.userID+"&event_type="+self.categoryTextField.text+"&game_name="+self.gameNameTextField.text+"&event_time="+self.eventTimePOST+"&time_ending="+self.gameTimeEndPOST
        
        request.HTTPBody = postParams.dataUsingEncoding(NSUTF8StringEncoding, allowLossyConversion: false)

        //sending the request
        NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue(), completionHandler:{ (response:NSURLResponse!, data: NSData!, error: NSError!) -> Void in
            var error: AutoreleasingUnsafeMutablePointer<NSError?> = nil
            let jsonResult: NSDictionary! = NSJSONSerialization.JSONObjectWithData(data, options:NSJSONReadingOptions.MutableContainers, error: error) as? NSDictionary
            
            if (jsonResult != nil) {
                // process jsonResult - assigning values to labels
                //self.userName.text = (jsonResult.objectForKey("name") as! String)
                //self.numberWins.text = toString(jsonResult.objectForKey("wins") as! NSNumber)
                //self.numberPoints.text = toString(jsonResult.objectForKey("points") as! NSNumber)
                
            } else {
                // couldn't load JSON, look at error
                println("return is null")
            }
        })
    }
    
    //TODO: add players to the game
    ///http://173.236.253.103:28080/game_data/add_user_to_game/
    //userid, gameid
    
}
