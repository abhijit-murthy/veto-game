//
//  gameScreenViewController.swift
//  vetoGame
//
//  Created by Cristina on 4/21/15.
//  Copyright (c) 2015 CristinaChu. All rights reserved.
//

import Foundation
import UIKit

class gameScreenViewController : UIViewController {
    
    @IBOutlet weak var businessName: UIButton!
    @IBOutlet weak var address: UILabel!
    @IBOutlet weak var suggestionTTL: UILabel!
    @IBOutlet weak var numberOfSupportersLabel: UILabel!
    @IBOutlet weak var supportButton: UIButton!
    @IBOutlet weak var vetoButton: UIButton!
    
    var numberOfSupporters : Int!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func addSupport(sender: AnyObject) {
        self.numberOfSupporters = self.numberOfSupporters+1
        self.numberOfSupportersLabel.text = toString(self.numberOfSupporters)
        
        //TODO: send POST request to show change
    }
    
}