/*global module:true, require:true, console:true, process:true */

'use strict';

var restify = require('restify')
  , db = require('../db')
  , sequelize = require('sequelize');

exports.endpointBase = '/user_data';
/**
	@api {post} /user_data/create Create a new User
	@apiName CreateUser
	@apiGroup User

	@apiParam {String} user_id 	Users Facebook ID (remember to get this from the Facebook API)
	@apiParam {String} user_name Users Name

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
	.then(
		function(user){
			if(user != null)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("User already exists"));
			else
				return db.createUser(req.params.user_id,req.params.user_name);
		}
	)
	.then(
		function(user){
			return res.send({id: user.id});
		},
		function(error){
			return res.send(error);
		}
	);
};
exports.createUser = createUser;
exports.createUserEndpoint = exports.endpointBase + '/create';

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
	db.getUser(req.params.id)
	.then(
		function(user){
			if(user != null){
				res.send(user.values);
			}else {
				res.send(new restify.InvalidArgumentError("Bad User Id"));
			}
		}
	);
	next();
}
exports.getUserData = getUserData;
exports.getUserDataEndpoint = exports.endpointBase + '/:id';

/**
	@api {get} /user_data/get_game_users/:game_id Get Users in a Game
	@apiName GetGameUsers
	@apiGroup User

	@apiParam {String} game_id ID of the Game

	@apiSuccess {Array} users List of Users in the Game

	@apiError InvalidArgumentError Bad Game ID

	@apiUse GameFinished
*/
function getGameUsers(req,res,next){
	var game;
	var gameFinished;
	db.getGame(req.params.game_id)
	.then(function(result){
		if(result == null){
			return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad Game ID"));
		}else {
			game = result;
			return db.isGameFinished(game);
		}
	})
	.then(
		function(isGameFinished){
			gameFinished = isGameFinished;
			return db.getGameUsers(game);
		}
	)
	.then(
		function(users){
			var ret = {};
			ret.users = users;
			if(gameFinished){
				ret.code = "GameFinished";
				ret.message = "Game is Finished";
			}
			return ret;
		}
	)
	.then(
		function(result){
			res.send(result);
		}
	)
	.error(
		function(err){
			res.send(err);
		}
	);
	next();
}
exports.getGameUsers = getGameUsers;
exports.getGameUsersEndpoint = exports.endpointBase + '/get_game_users/:game_id';
