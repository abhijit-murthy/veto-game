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
    var userID : String!
    var fbSession : FBSession!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
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
        
        if (segue.identifier == "logout"){
            //var destViewController : facebookLoginViewController = segue.destinationViewController as facebookLoginViewController
            
            //TODO Restart App
        }
    }

}

