//
//  inviteFriendsViewController.swift
//  vetoGame
//
//  Created by Cristina on 4/9/15.
//  Copyright (c) 2015 CristinaChu. All rights reserved.
//

import Foundation
import UIKit

class selectPlayersViewController : UITableViewController {

    let friends : [NSArray] = [["ABMURTHY", "Abhijit Murthy"], ["EKIM305", "Eunki Kim"], ["IAN1639", "Ian Stainbrook"], ["VIRAL9793", "Viral Patel"]]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //something
    }
    
    //TODO: get friends directly from Facebook
    func getFacebookFriends () {
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
    
    func getFriends() {
        
        ///http://173.236.253.103:28080/game_data/add_user_to_game/
        //userid, gameid
    }
    
}