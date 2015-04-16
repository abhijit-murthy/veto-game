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
	@api {post} /suggestion_data/upvote Upvote
	@apiDescription Upvotes a game's current suggestion (provided it matches the suggestion parameter)
	@apiName Upvote
	@apiGroup Suggestion

	@apiParam {String} game_id	Game ID
	@apiParam {String} suggestion_id	Current Suggestion ID

	@apiSuccess (200) {Integer} votes Current number of upvotes

	@apiError InvalidArgumentError 	Bad Game ID
	@apiError InvalidArgumentError 	Bad Suggestion ID
	@apiError InvalidArgumentError	Mismatch

	@apiUse GameFinished
*/
function upvote(req,res,next){
	var game, suggestion;
	db.getGame(req.params.game_id)
	.then(
		function(result){
			if(result == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad Game ID"));
			}else {
				game = result;
				return db.isGameFinished(game);
			}
		}
	).then(
		function(isGameFinished){
			if(isGameFinished)
				return sequelize.Promise.reject({code: "GameFinished",message: "Game has ended"});
			else
				return db.getSuggestion(req.params.suggestion_id);
		}
	).then(
		function(result){
			if(result == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad Suggestion ID"));
			}else {
				suggestion = result;
				return db.upvote(game, suggestion);
			}
		}
	).then(
		function(result){
			res.send({votes: result});
		}
	).error(
		function(error){
			//res.send({count: error});
			if(error == -1)
				res.send(new restify.InvalidArgumentError("Mismatch"));
			else
				res.send(error);
		}
	);
}
exports.upvote = upvote;
exports.upvoteEndpoint = exports.endpointBase + '/upvote';




/**
	@api {post} /suggestion_data/veto Veto
	@apiDescription Veto a game's current suggestion and make a new suggestion
	@apiName Veto & Suggest New
	@apiGroup Suggestion

	@apiParam {String} game_id	Game ID
	@apiParam {String} user_id	User ID
	@apiParam {String} curr_suggestion_id	Current Suggestion ID
	@apiParam {String} new_suggestion_name	New Suggestion Name
	@apiParam {String} new_suggestion_loc	New Suggestion Location

	@apiSuccess (200) {Integer} id ID of new suggestion

	@apiError InvalidArgumentError 	Bad Game ID
	@apiError InvalidArgumentError 	Bad Suggestion ID
	@apiError InvalidArgumentError 	Bad User ID
	@apiError InvalidArgumentError 	No new suggestion name
	@apiError InvalidArgumentError 	No new suggestion location
	@apiError InvalidArgumentError	Mismatch

	@apiUse GameFinished
*/
function veto(req,res,next){
	var game, user, suggestionToVeto;
	db.getGame(req.params.game_id)
	.then(
		function(result){
			if(result == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad Game ID"));
			}else {
				game = result;
				return db.isGameFinished(game);
			}
		}
	).then(
		function(isGameFinished){
			if(isGameFinished){
				return sequelize.Promise.reject({code: "GameFinished",message: "Game has ended"});
			}else {
				return db.getSuggestion(req.params.suggestion_id);
			}
		}
	).then(
		function(result){
			if(result == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad Suggestion ID"));
			}else {
				suggestionToVeto = result;
				//return db.upvote(game, suggestion);
				return db.getUser(req.params.user_id);
			}
		}
	).then(
		function(result){
			if(result == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad User ID"));
			}else {
				user = result;
				//return db.upvote(game, suggestion);
				//return db.getUser(req.params.user_id
				
				if(!req.params.new_suggestion_name){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No new suggestion name"));
				}
				if(!req.params.new_suggestion_loc){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("No new suggestion location"));
				}
				
				return db.veto(game, user, suggestionToVeto, req.params.new_suggestion_name, req.params.new_suggestion_loc);				
				
			}
		}
	).then(
		function(result){
			res.send({id: result});
		}
	).error(
		function(error){
			//res.send({id: error});
			if(error == -1)
				res.send(new restify.InvalidArgumentError("Mismatch"));
			else
				res.send(error);
		}
	);
}
exports.veto = veto;
exports.vetoEndpoint = exports.endpointBase + '/veto';




/**
	@api {get} /suggestion_data/game_history/:id Get Suggestion history
	@apiDescription Gets the Suggestion history for an ongoing Game
	@apiName GetGameSuggestionHistory
	@apiGroup Suggestion

	@apiParam {String} id The Game ID

	@apiSuccess (200) {Array} Unamed An array of Suggestions

	@apiError InvalidArgumentError Bad Game Id
	@apiUse GameFinished
*/
function getGameSuggestionHistory(req,res,next){
	var game;
	db.getGame(req.params.id)

	.then(
		function(result){
			if(result == null)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad Game ID"));
			else{
				game = result;
				return db.isGameFinished(game);
			}
		}
	).then(
		function(isGameFinished){
			if(isGameFinished)
				return sequelize.Promise.reject({code: "GameFinished",message: "Game has ended"});
			else
				return db.getGameSuggestionHistory(game);
		}
	).then(
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

	@apiSuccess (200) {String}	id 	The suggestion ID
	@apiSuccess (200) {String}	name The name of the suggestion
	@apiSuccess (200) {String}  location The location of the suggestion
	@apiSuccess (200) {String}	votes The current votes of the suggestion
	@apiSuccess (200) {String}  gameId The game the suggestion is attached to
	@apiSuccess (200) {String} 	UserId The user that suggested the game

	@apiError InvalidArgumentError Bad Game Id
	@apiUse GameFinished
*/
function getCurrentSuggestion(req,res,next){
	var game;
	db.getGame(req.params.id)

	.then(
		function(result){
			if(result == null)
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad Game Id"));
			else{
				game = result;
				return db.getCurrentSuggestion(game);
			}
		}
	).then(
		function(isGameFinished){
			if(isGameFinished)
				return sequelize.Promise.reject({code: "GameFinished",message: "Game has ended"});
			else
				return db.getCurrentSuggestion(game);
		}
	).then(
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
	@api {get} /suggestion_data/yelp_suggestions/:id Get Suggestions from Yelp Api
	@apiDescription Gets Suggestions from the Yelp API using information about the Game.
	@apiName GetYelpSuggestions
	@apiGroup Suggestion

	@apiParam {String} game_id	Game to get information from

	@apiSuccess (200) Suggestions from Yelp

	@apiError InvalidArgumentError 	Bad Game Id
*/
function getYelpSuggestions(req, res, next) {
	db.getGame(req.params.game_id)
	.then(
		function(result){
			if(result == null){
				return sequelize.Promise.reject(new restify.InvalidArgumentError("Bad game Id"));
			}
			else {
				return result;
			}
		}
	).then(
		function(game){
			
			console.log(game.radius);
			var location = 'location='+game.center+'&'
			  , radius_filter = 'radius_filter='+game.radius+'&'
			  , category_filter = 'term='+game.eventType+'&' //need to check supported categories or use term
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
			}, function(err, response, feed) {
			    //console.log(feed);
			    console.log("Amount returned" + feed.businesses.length);
			    res.send(feed);
			    //TODO: do whatever needs to be done with information (json)
				});
		}
	).error(
		function(err){
			res.send(err);
		}
	);
}
exports.getYelpSuggestions = getYelpSuggestions;
exports.getYelpSuggestionsEndpoint = exports.endpointBase + '/yelp_suggestions/:game_id';


/**
	@api {get} /suggestion_data/yelp_suggestions_initial?center=:center&event_type=:event_type&radius=:radius Get Suggestions from Yelp Api (for initial suggestion)
	@apiDescription Gets Suggestions from the Yelp API using URL parameters
	@apiName GetYelpSuggestionsInitial
	@apiGroup Suggestion

	@apiParam {String} center	Center location
	@apiParam {String} radius	Radius to search
	@apiParam {String} event_type	Category

	@apiSuccess (200) Suggestions from Yelp

*/
function getYelpSuggestionsInitial(req, res, next) {

			var location = 'location='+req.params.center+'&'
			  , radius_filter = 'radius_filter='+req.params.radius+'&'
			  , category_filter = 'term='+req.params.event_type+'&' //need to check supported categories or use term
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
			}, function(err, response, feed) {
			    //console.log(feed);
			    console.log("Amount returned" + feed.businesses.length);
			    res.send(feed);
			    //TODO: do whatever needs to be done with information (json)
				});

}
exports.getYelpSuggestionsInitial = getYelpSuggestionsInitial;
exports.getYelpSuggestionsInitialEndpoint = exports.endpointBase + '/yelp_suggestions_initial';
