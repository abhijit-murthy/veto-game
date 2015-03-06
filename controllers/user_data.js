/*global module:true, require:true, console:true, process:true */



'use strict';

var restify = require('restify')
  , db = require('../db');
/**
	@api {post} /user_data/create Create a new User
	@apiName CreateUser
	@apiGroup User

	@apiParam {String} user_id 	Users Facebook ID (remember to get this from the Facebook API)
	@apiParam {String} name Users Name

	@apiSuccess (200)	Just the HTTP 200 Response

	@apiError InvalidArgumentError User already exists
*/
function createUser(req,res,next){
	// db.getUser(req.params.user_id,function(user){
	// 	if(user != null){
	// 		res.send(new restify.InvalidArgumentError("User already exists"));
	// 	}else {
	// 		db.createUser(req.params.user_id,
	// 			req.params.user_name,
	// 			function(){
	// 				res.send(200);
	// 			});
	// 	}
	// });
	db.getUser(req.params.user_id)
	.then(function(user){
		if(user != null)
			res.send(new restify.InvalidArgumentError("User already exists" + user));
		else
			return db.createUser(req.params.user_id,req.params.user_name);
	})
	.then(function(user){
		return res.send(user.id);
	});
};
exports.createUser = createUser;

/**
	@api {get} /user_data/:id Get User information
	@apiName GetUserData
	@apiGroup User

	@apiParam {String} id 	Users Facebook ID

	@apiSuccess {String} id 	Users Facebook ID
	@apiSuccess {String} name 	Users Name
	@apiSuccess {Integer} wins	Number of games the User has won
	@apiSuccess {Integer} points 	Number of points the User has accumulated
	@apiSuccess {Integer} extras 	Number of extra vetoes they have accumulated

	@apiError InvalidArgumentError Thrown when the user queried does not exist 
*/
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
