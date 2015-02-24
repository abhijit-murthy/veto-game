/*global module:true, require:true, console:true, process:true */

'use strict';

var restify = require('restify')
  , db = require('../db');

function createUser(req,res,next){
	db.getUser(req.params.user_id,function(user){
		if(user != null){
			res.send(new restify.InvalidArgumentError("User already exists"));
		}else {
			db.createUser(req.params.user_id,
				req.params.user_name,
				function(){
					res.send(200);
				});
		}
	});
}
exports.createUser = createUser;

function getUserData(req,res,next){
	db.getUser(req.params.id,function(user){
		if(user != null){
			res.send(user.values);
		}else {
			res.send(new restify.InvalidArgumentError("Bad User Id"));
		}
	});
	next();
}
exports.getUserData = getUserData;
