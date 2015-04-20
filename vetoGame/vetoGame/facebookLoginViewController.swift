//
//  facebookLoginViewController.swift
//  vetoGame
//
//  Created by Cristina on 3/23/15.
//  Copyright (c) 2015 CristinaChu. All rights reserved.
//

import Foundation
import UIKit

class facebookLoginViewController : UIViewController, FBLoginViewDelegate {
    
    @IBOutlet var fbLoginView : FBLoginView!
    var userID : String!

    override func viewDidLoad() {
        super.viewDidLoad()
        self.fbLoginView.delegate = self
        self.fbLoginView.readPermissions = ["public_profile", "email", "user_friends"]
    }
    
    func loginViewShowingLoggedInUser(loginView : FBLoginView!) {
        //Segue to go to Home Page after logging in
        self.performSegueWithIdentifier("afterLogin", sender: self)
    }
    
    func loginViewFetchedUserInfo(loginView : FBLoginView!, user: FBGraphUser){
        println("User Name: \(user.name)")
        
        self.userID = user.objectForKey("id") as! String
        
    }
    
    func loginViewShowingLoggedOutUser(loginView : FBLoginView!) {
        println("User Logged Out")
    }
    
    func loginView(loginView : FBLoginView!, handleError:NSError) {
        println("Error: \(handleError.localizedDescription)")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        //this segue should be afterLogin
        if (segue.identifier == "mainPage"){
            var navigationController : UINavigationController = segue.destinationViewController as! UINavigationController
        
            var destViewController : ViewController = navigationController.topViewController as! ViewController
            destViewController.fbSession = FBSession.activeSession()
            
            //Take this out at the end
            self.userID = "ABC"
            destViewController.userID = self.userID
        }
    }
    
}