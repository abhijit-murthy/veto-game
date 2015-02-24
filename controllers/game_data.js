/*global module:true, require:true, console:true, process:true */

'use strict';

var restify = require('restify')
  , db = require('../db');

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
