//
//  initialSuggestionListViewController.swift
//  vetoGame
//
//  Created by Cristina on 4/9/15.
//  Copyright (c) 2015 CristinaChu. All rights reserved.
//

import Foundation
import UIKit

class initialSuggestionListViewController : UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    
    var swiftBlogs = ["Ray Wenderlich", "NSHipster", "iOS Developer Tips", "Jameson Quave", "Natasha The Robot", "Coding Explorer", "That Thing In Swift", "Andrew Bancroft", "iAchieved.it", "Airspeed Velocity"]

    var eventType : String!
    var radius : String!
    var center : String!
    var mapView : initialSuggestionMapViewController!
    
    var businesses = [NSArray]()
    var total : Int = 0
    
    /*
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.delegate = self
        tableView.dataSource = self
    }*/
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(false)
        getYelpSuggestions()
        
        tableView.delegate = self
        tableView.dataSource = self
    }
    
    //MARK: TableView Code
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return swiftBlogs.count
    }

    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell : UITableViewCell = tableView.dequeueReusableCellWithIdentifier("businessCell", forIndexPath: indexPath) as! UITableViewCell
        
        let row = indexPath.row
        cell.textLabel?.text = swiftBlogs[row]
        
        return cell
    }

    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: true)
        
        let row = indexPath.row
        println(swiftBlogs[row])
    }
    
    //MARK: API call to Yelp for suggestions
    func getYelpSuggestions() {
        self.radius = mapView.radiusValue.text
        self.center = mapView.center
        
        var url : String = "http://173.236.253.103:28080/suggestion_data/yelp_suggestions_initial?center="+self.center+"&event_type="+self.eventType+"&radius="+self.radius
        var request : NSMutableURLRequest = NSMutableURLRequest()
        
        request.URL = NSURL(string: url)
        request.HTTPMethod = "GET"
        
        NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue(), completionHandler:{ (response:NSURLResponse!, data: NSData!, error: NSError!) -> Void in
            var error: AutoreleasingUnsafeMutablePointer<NSError?> = nil
            let jsonResult: NSDictionary! = NSJSONSerialization.JSONObjectWithData(data, options:NSJSONReadingOptions.MutableContainers, error: error) as? NSDictionary
            
            if (jsonResult != nil) {
                println(jsonResult)
                self.swiftBlogs.append("YELP")
                // process jsonResult - assigning values to labels
                //self.userName.text = (jsonResult.objectForKey("name") as! String)
                //self.numberWins.text = toString(jsonResult.objectForKey("wins") as! NSNumber)
                //self.numberPoints.text = toString(jsonResult.objectForKey("points") as! NSNumber)
                
            } else {
                // couldn't load JSON, look at error
                println("No businesses around")
            }
        })
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}