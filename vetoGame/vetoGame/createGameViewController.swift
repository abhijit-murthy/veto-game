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
    
    let date = NSDate()
    var userID: String!
    
    @IBOutlet weak var gameNameTextField: UITextField!
    @IBOutlet weak var categoryTextField: UITextField!
    @IBOutlet weak var inviteFriendsButton: UIButton!
    @IBOutlet weak var eventDate: UITextField!
    @IBOutlet weak var gameEndTime: UITextField!
    @IBOutlet weak var locationTextField: UITextField!
    @IBOutlet weak var initialSuggestionButton: UIButton!
    @IBOutlet weak var createGameButton: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        println(userID)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @IBAction func selectEventDate(sender: UITextField) {
        var datePickerView  : UIDatePicker = UIDatePicker()
        
        datePickerView.datePickerMode = UIDatePickerMode.DateAndTime
        sender.inputView = datePickerView
        datePickerView.minimumDate = date
        datePickerView.addTarget(self, action: Selector("handleDateStart:"), forControlEvents: UIControlEvents.ValueChanged)
        
        //TODO: make start date only possible for up to a certain amount of days (datePickerView.maximumDate)
    }
    
    
    @IBAction func selectGameEndTime(sender: UITextField) {
        var datePickerView  : UIDatePicker = UIDatePicker()
        
        datePickerView.datePickerMode = UIDatePickerMode.DateAndTime
        sender.inputView = datePickerView
        datePickerView.addTarget(self, action: Selector("handleDateEnd:"), forControlEvents: UIControlEvents.ValueChanged)
        
        //TODO: make ending date only possible for later dates and up to a certain amount of days
        datePickerView.minimumDate = date
    }
    
    func handleDateStart(sender: UIDatePicker) {
        var timeFormatter = NSDateFormatter()
        timeFormatter.dateStyle = .LongStyle
        timeFormatter.timeStyle = .ShortStyle
        eventDate.text = timeFormatter.stringFromDate(sender.date)
    }

    func handleDateEnd(sender: UIDatePicker) {
        var timeFormatter = NSDateFormatter()
        timeFormatter.dateStyle = .LongStyle
        timeFormatter.timeStyle = .ShortStyle
        gameEndTime.text = timeFormatter.stringFromDate(sender.date)
    }
    
    
    @IBAction func createGame(sender: AnyObject) {
        //TODO: Call database API to create new game
        //TODO: Create new game in the app
            //TODO: Make sure that all elements are valid/exist
        
        
        //Going back to the Home page
        self.navigationController?.popViewControllerAnimated(true)
    }
    
    /*
    override func touchesBegan(touches: NSSet, withEvent event: UIEvent) {
        view.endEditing(true)
        super.touchesBegan(touches, withEvent: event)
    }*/
    
}
