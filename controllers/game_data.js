/*global module:true, require:true, console:true, process:true */

'use strict';

var restify = require('restify')
  , db = require('../db');
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

	@apiSuccess (200) {Integer} game_id 	The ID of the newly created game

	@apiError InvalidArgumentError Bad User ID
	@apiError InvalidArgumentError Bad Event Type
	@apiError InvalidArgumentError No Suggestion TTL
	@apiError InvalidArgumentError No Center
	@apiError InvalidArgumentError No Radius
*/
function createGame(req,res,next){
	console.log(req.params.user_id);
	db.getUser(req.params.user_id,function(user){
		console.log(user);
		if(user == null){
			res.send(new restify.InvalidArgumentError("Bad user ID"));
			return;
		}
		var eventType = req.params.event_type;
		if(eventType != 'dinner' &&
			eventType != 'lunch' &&
			eventType != 'breakfast'){
			res.send(new restify.InvalidArgumentError("Bad event type"));
			return;
		}
		var suggestionTTL = req.params.suggestion_ttl;
		if(!suggestionTTL){
			res.send(new restify.InvalidArgumentError("No Suggestion TTL"));
			return;
		}
		var center = req.params.center;
		if(!center){
			res.send(new restify.InvalidArgumentError("No Center"));
			return;
		}
		var radius = req.params.radius;
		if(!radius){
			res.send(new restify.InvalidArgumentError("No radius"));
			return;
		}
		db.createGame(eventType,suggestionTTL,center,radius,user,function(game){
			res.send({game_id: game.values.id});
		});
	});
	next();
}
exports.createGame = createGame;

/**
	@api {get} /game_data/:id Get Game information
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
*/
function getGameData(req,res,next){
	var gameId = req.params.id;
	db.getGame(gameId,function(game){
		if(game != null)
			res.send(game.values);
		else
			res.send(new restify.InvalidArgumentError("Bad game ID"));
	});
	next();
}
exports.getGameData = getGameData;
