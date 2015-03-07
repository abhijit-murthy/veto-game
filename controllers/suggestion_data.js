/*global module:true, require:true, console:true, process:true */

'use strict'

var restify = require('restify')
  , db = require('../db')
  , sequelize = require('sequelize');
/**
	@api {post} /suggestion_data/create Create a new Suggestion
	@apiDescription Creates a new Suggestion for a given User and adds it to a given Game as the current Suggestion.
	@apiName CreateSuggestion
	@apiGroup Suggestion

	@apiParam {String} user_id	Creating User's Facebook ID
	@apiParam {String} game_id	Game to add to
	@apiParam {String} name Name of the location
	@apiParam {String} location 	Location of the suggestion

	@apiSuccess (200) {Integer} id The ID of the newly created suggestion

	@apiError InvalidArgumentError 	Bad User ID
	@apiError InvalidArgumentError 	Bad game Id
	@apiError InvalidArgumentError 	No name supplied
	@apiError InvalidArgumentError 	No location supplied
*/
function createSuggestion(req,res,next){
	var user,game;
	db.getUser(req.params.user_id)
	.then(
		function(result){
			if(result == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad user Id"));
			}else {
				user = result;
				return db.getGame(req.params.game_id);
			}
		}
	).then(
		function(result){
			if(result == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad game Id"));
			}else {
				game = result;
			}
		}
	).then(
		function(){
			if(!req.params.name){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No name supplied"));
			}
			if(!req.params.location){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No location supplied"));
			}
			return db.addSuggestion(req.params.name,req.params.location,user,game);
		}
	).then(
		function(suggestion){
			res.send({id: suggestion.values.id});
		}
	).error(
		function(error){
			res.send(error);
		}
	);
}
exports.createSuggestion = createSuggestion;