/*global module:true, require:true, console:true, process:true */

'use strict'

var restify = require('restify')
  , db = require('../db')
  , sequelize = require('sequelize')
  , OAuth = require('oauth-request');

exports.endpointBase = '/suggestion_data';

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
exports.createSuggestionEndpoint = exports.endpointBase + '/create';

/**
	@api {get} /suggestion_data/game_history/:id Get Suggestion history
	@apiDescription Gets the Suggestion history for an ongoing Game
	@apiName GetGameSuggestionHistory
	@apiGroup Suggestion

	@apiParam {String} id The Game ID

	@apiSuccess (200) {Array} Unamed An array of Suggestions

	@apiError InvalidArgumentError Bad Game Id
*/
function getGameSuggestionHistory(req,res,next){
	db.getGame(req.params.id)

	.then(
		function(game){
			if(game == null)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad Game ID"));
			else
				return db.getGameSuggestionHistory(game);
		}
	)
	.then(
		function(suggestions){
			if(suggestions != null)
				res.send(suggestions);
		}
	)
	.error(
		function(err){
			res.send(err);
		}
	);
	next();
}
exports.getGameSuggestionHistory = getGameSuggestionHistory;
exports.getGameSuggestionHistoryEndpoint = exports.endpointBase + '/game_history/:id';

/**
	@api {get} /suggestion_data/current_suggestion/:id Get current Suggestion
	@apiDescription Gets the current Suggestion in an ongoing Game
	@apiName GetCurrentSuggestion
	@apiGroup Suggestion

	@apiParam {String} id The Game ID

	@apiSuccess (200) {Suggestion} Unamed The current Suggestion

	@apiError InvalidArgumentError Bad Game Id
*/
function getCurrentSuggestion(req,res,next){
	db.getGame(req.params.id)

	.then(
		function(game){
			if(game == null)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad Game Id"));
			else
				return db.getCurrentSuggestion(game);
		}
	)
	.then(
		function(suggestion){
			if(suggestion != null)
				res.send(suggestion);
		}
	)
	.error(
		function(err){
			res.send(err);
		}
	);
	next();
}
exports.getCurrentSuggestion = getCurrentSuggestion;
exports.getCurrentSuggestionEndpoint = exports.endpointBase + '/current_suggestion/:id';

/**
	@api {get} Get suggestions from Yelp Api
	@apiDescription Gets suggestions from the Yelp API using information about the game.
	@apiName getYelpSuggestions
	@apiGroup Suggestion

	@apiParam {String} game_id	Game to get information from

	@apiSuccess (200) 

	@apiError InvalidArgumentError 	Bad game Id
*/
function getYelpSuggestions(req, res, next) {
	var game;
	db.getGame(req.params.game_id)
	.then(
		function(result){
			if(result == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad game Id"));
			}
			else {
				game = result;
			}
		}
	).then(

	)


//
	var location = 'location='+game.center+'&'
	  , radius_filter = 'radius_filter='+game.radius+'&'
	  , category_filter = 'category_filter='+game.event_type+'&' //need to check supported categories or use term
	  , sort = 'sort=1'; //sort by distance

	var search = 'https://api.yelp.com/v2/search?'+location+radius_filter+category_filter+sort;

	var yelp = OAuth({
	    consumer: {
	        public: 'pI2SJJxe5YBSJ6OjLrWUOQ',
	        secret: 'PfUhZKeyTza3VpMfrgn8CuBDynQ'
	    }
	});

	yelp.setToken({
	    public: 'vcn_ks3F6lDaGJsG4MApdxkk1XDqOT5x', 
	    secret: '86r0tTOvF978mE7QKnE2-5Mlp5Q'
	});

	yelp.get({
	    url: search,
	    qs: {
	        count: 5
	    },
	    json: true
	}, function(err, res, feed) {
	    //console.log(feed);
	    res.send(feed);
	    //TODO: do whatever needs to be done with information (json)
		});
}

//export yelpsuggestions

