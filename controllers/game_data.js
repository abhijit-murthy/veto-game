/*global module:true, require:true, console:true, process:true */

'use strict';

var restify = require('restify')
  , db = require('../db')
  , sequelize = require('sequelize');

exports.endpointBase = '/game_data';
/**
	@apiDefine GameFinished
	@apiError GameFinished Game is finished
	@apiErrorExample {json} GameFinished:
		HTTP/1.1 200 OK
		{
			"code": "GameFinished",
			"message": "Game has ended"
		}
*/

/**
	@api {post} /game_data/create Create a new Game
	@apiDescription Creates a new Game and adds the creating User to it.
	@apiName CreateGame
	@apiGroup Game

	@apiParam {String} user_id  	Creating User's Facebook ID
	@apiParam {String} event_type 	Type of event. Must be one of 'dinner','lunch, or 'breakfast'
	@apiParam {Integer} suggestion_ttl 	Time to Live of a suggestion before the game ends (in minutes)
	@apiParam {Location} center 	Center of the circle in which we are filtering suggestions
	@apiParam {Integer} radius 	Radius of the circle in which we are filtering suggestions
	@apiParam {String} name Name of the Game being created
	@apiParam {String} event_time	Time of the event
	@apiParam {String} time_ending	Ending time of the Game

	@apiSuccess (200) {Integer} game_id 	The ID of the newly created game

	@apiError InvalidArgumentError Bad User ID
	@apiError InvalidArgumentError Bad Event Type
	@apiError InvalidArgumentError No Suggestion TTL
	@apiError InvalidArgumentError No Center
	@apiError InvalidArgumentError No Radius
*/
function createGame(req,res,next){
	db.getUser(req.params.user_id)
	.then(
		function(user){
			if(user == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad user ID"));
			}
			var eventType = req.params.event_type;
			if(eventType != 'dinner' &&
				eventType != 'lunch' &&
				eventType != 'breakfast'){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad event type"));
			}
			var suggestionTTL = req.params.suggestion_ttl;
			if(!suggestionTTL){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No Suggestion TTL"));
			}
			var center = req.params.center;
			if(!center){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No Center"));
			}
			var radius = req.params.radius;
			if(!radius){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No radius"));
			}

			var name = req.params.game_name;
			if(!name)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No Name"));

			var eventTime = req.params.event_time;
			if(!eventTime)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No Event Time"));
			
			var timeEnding = req.params.time_ending;
			if(!timeEnding)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No Time Ending"));

			return db.createGame(name,eventType,suggestionTTL,center,radius,user,eventTime,timeEnding);
		}
	)
	.then(
		function(game){
			res.send({game_id: game.values.id});
		},
		function(error){
			res.send(error);
		}
	);
	next();
}
exports.createGame = createGame;
exports.createGameEndpoint = exports.endpointBase + '/create'

/**
	@api {get} /game_data/:id Get Game information
	@apiDescription Get detailed Game information
	@apiName GetGameData
	@apiGroup Game

	@apiParam {String} id 	Game ID

	@apiSuccess {String} id  	Creating User's Facebook ID
	@apiSuccess {String} event_type 	Type of event. Must be one of 'dinner','lunch, or 'breakfast'
	@apiSuccess {DateTime} eventTime 	The time of the event 
	@apiSuccess {DateTime} timeEnding 	Time the game ends
	@apiSuccess {Integer} suggestion_ttl 	Time to Live of a suggestion before the game ends (in minutes)
	@apiSuccess {Location} center 	Center of the circle in which we are filtering suggestions
	@apiSuccess {Integer} radius 	Radius of the circle in which we are filtering suggestions

	@apiError InvalidArgumentError Bad Game ID 

	@apiUse GameFinished
*/
function getGameData(req,res,next){
	var gameId = req.params.id;
	var game;
	db.getGame(gameId)
	.then(
		function(result){
			if(result != null){
				game = result;
				return db.isGameFinished(result);
			}
			else{
				res.send(new restify.InvalidArgumentError("Bad game ID"));
			}
		}
	).then(
		function(isFinished){
			if(isFinished){
				res.send({code: "GameFinished",message: "Game has ended"});
			}else {
				res.send(game.values);
			}
		}
	);
	next();
}
exports.getGameData = getGameData;
exports.getGameDataEndpoint = exports.endpointBase + '/:id';

/**
	@api {post} /game_data/add_user_to_game Add a given user to a Game
	@apiName AddUserToGame
	@apiGroup Game

	@apiParam {String} user_id Creating User's id
	@apiParam {String} game_id Target Game id

	@apiSuccess {String} user_id Creating User's id
	@apiSuccess {String} game_id Target Game id

	@apiError InvalidArgumentError Bad Game/User id, Could not add user to game
*/
function addUserToGame(req,res,next){
	var gameId = req.params.game_id;
	var game,user;
	db.getGame(gameId)
	.then(
		function(result){
			if(result != null){
				game = result;
				return db.getUser(req.params.user_id);
			}else{
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad game ID"));
			}
		}
	).then(
		function(result){
			if(result != null){
				user = result;
				return db.addUserToGame(game,user);
			}else {
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad User ID"));
			}
		}
	).then(
		function(gameUser){
			if(gameUser != null){
				res.send({user_id: gameUser.UserId,game_id:gameUser.GameId});
			}else {
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Could not add user to Game"));
			}
		}
	).error(
		function(err){
			res.send(err);
		}
	);
}
exports.addUserToGame = addUserToGame;
exports.addUserToGameEndpoint = exports.endpointBase + '/add_user_to_game';
/**
	@api {get} /game_data/get_user_games/:id Get User's Games
	@apiDescription Get all the Games that a given User is associated with
	@apiName GetUserGames
	@apiGroup Game

	@apiParam {String} id The requesting User's ID

	@apiSuccess {Array} Games that the User is a part of

	@apiError InvalidArgumentError Bad User ID/Could not retrieve list of games
*/
function getUserGames(req,res,next){
	db.getUser(req.params.id)

	.then(
		function(user){
			if(user == null)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad User ID"));
			else
				return db.getUserGames(user);
		}
	)
	.then(
		function(games){
			if(games == null)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Could not retrieve Games"));
			else
				res.send(games);
		}
	)
	.error(
		function(err){
			res.send(err);
		}
	);
	next();
}
exports.getUserGames = getUserGames;
exports.getUserGamesEndpoint = exports.endpointBase + '/get_user_games/:id';

