//
//  inviteFriendsViewController.swift
//  vetoGame
//
//  Created by Cristina on 4/9/15.
//  Copyright (c) 2015 CristinaChu. All rights reserved.
//

import Foundation
import UIKit

class inviteFriendsViewController : UITableViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //something
    }
    
    func getFriends () {
        var friendsRequest: FBRequest = FBRequest.requestForMyFriends()
        /*
        friendsRequest.startWithCompletionHandler{(connection:FBRequestConnection!, result:AnyObject, error:NSError!) -> Void in
            var resultdict = result as NSDictionary
            println("Result Dict: \(resultdict)")
            
            var data : NSArray = resultdict.objectForKey("data") as NSArray
            println(data)
            
            for i in 0...data.count {
                let valueDict : NSDictionary = data[i] as NSDictionary
                let id = valueDict.objectForKey("id") as String
                println("the id value is \(id)")
            }
            
            var friends = resultdict.objectForKey("data") as NSArray
            println("Found \(friends.count) friends")
        }
        */
    }
    
}