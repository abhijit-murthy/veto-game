//
//  initialSuggestionMapViewController.swift
//  vetoGame
//
//  Created by Cristina on 4/9/15.
//  Copyright (c) 2015 CristinaChu. All rights reserved.
//

import Foundation
import UIKit
import MapKit
import CoreLocation

class initialSuggestionMapViewController : UIViewController , CLLocationManagerDelegate, MKMapViewDelegate {
    
    @IBOutlet weak var radiusSlider: UISlider!
    @IBOutlet weak var radiusValue: UILabel!
    @IBOutlet weak var map: MKMapView!
    
    let locationManager = CLLocationManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //something
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func getCurrentLocation(sender: AnyObject) {
        self.locationManager.delegate = self
        self.locationManager.desiredAccuracy = kCLLocationAccuracyBest
        self.locationManager.startUpdatingLocation()
    }
    
    /*
    func locationManager(manager: CLLocationManager!, didUpdateLocations locations: [AnyObject]!) {
        CLGeocoder().reverseGeocodeLocation(manager.location, completionHandler: {(placemarks, error)->Void in
            
            if (error != nil) {
                println("Reverse geocoder failed with error" + error.localizedDescription)
                return
            }
            
            if placemarks.count > 0 {
                let pm = placemarks[0] as! CLPlacemark
                self.displayLocationInfo(pm)
            } else {
                println("Problem with the data received from geocoder")
            }
        })
    }*/
    
    func locationManager(manager: CLLocationManager!, didFailWithError error: NSError!) {
        println("Error while updating location " + error.localizedDescription)
    }
    
    @IBAction func changingRadiusValue(sender: UISlider) {
        var currentValue = Int(sender.value)
        
        self.radiusValue.text = toString(currentValue)
    }
    
}