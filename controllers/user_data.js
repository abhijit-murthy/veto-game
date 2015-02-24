'use strict';

var restify = require('restify');
var db = require('../db');

function createUser(req,res,next){
	db.getUser(req.params.user_id, function(user){
		if (user == null) {
			db.createUser(req.params.user_id, req.params.user_name, function(user){
				res.send({user_id: user.values.id});
			});
		}
		else
			res.send(new restify.InvalidArgumentError("User ID exists already"));
	});
	next();
}
exports.createUser = createUser;

function getUserData(req,res,next){
	var userID = req.params.user_id;

	db.getUser(req.params.user_id, function(user) {
		if (user != null)
			res.send(user.values);
		else
			res.send(new restify.InvalidArgumentError("Bad User ID"));
	});
	next();
}
exports.getUserData = getUserData;
