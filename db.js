/*global module:true, require:true, console:true, process:true */

'use strict';

var fs = require('fs');
var Sequelize = require('sequelize');
var sequelize;
var async = require('async');

var credentials_filename = "secure_this_file.txt";
var db_name = "test";
var mysql_port = 3306;

var User, Suggestion, Game;

exports.initDB = initDB;
exports.createUser = createUser;
exports.getUser = getUser;
exports.createGame = createGame;
exports.getGame = getGame;
exports.addSuggestion = addSuggestion;
exports.addUserToGame = addUserToGame;
exports.getGameSuggestionHistory = getGameSuggestionHistory;
exports.getUserGames = getUserGames;
exports.getCurrentSuggestion = getCurrentSuggestion

function initDB ()
{
	async.waterfall([
	
		function(callback){		//getUsernamePassword
		
			var lines;
			fs.readFile(credentials_filename, 'utf8', function(err, data) {
				if (err) throw err;
				lines = data.split(/\r?\n/);
		
				if (lines === null || lines.length != 2)
				{
					console.log("Username/password format incorrect");
					console.log("GOT: " + lines);

				}
		
				callback(null, lines);
			});
		},
		
		function(credentials, callback){	//connect
		
			sequelize = new Sequelize(db_name, credentials[0], credentials[1], {
			dialect: "mysql",
			port:    mysql_port,
			});
		
			sequelize
			.authenticate()
			.complete(function(err) {
			if (!!err) {
				console.log('Unable to connect to the database:', err);
				callback(err);
			} else {
				console.log('Connection has been established successfully.');
				callback(null);
			}

			});
		
		},
		
		function(callback) {	//setupTables
		
			User = sequelize.define('User', {
				  id: {
					type: Sequelize.STRING,
					primaryKey: true
				  },
                 		  name: Sequelize.STRING,
                 		  wins: Sequelize.INTEGER.UNSIGNED,
				  points: Sequelize.INTEGER.UNSIGNED,
				  extras: Sequelize.INTEGER.UNSIGNED
                	});
				
			Suggestion = sequelize.define('Suggestion', {
				name: Sequelize.STRING,
				location: Sequelize.STRING,
				votes: Sequelize.INTEGER.UNSIGNED
				});
				
			Game = sequelize.define('Game', {
				eventTime: Sequelize.TIME,
				eventType: Sequelize.STRING,
				timeEnding: Sequelize.TIME,
				suggestionTTL: Sequelize.INTEGER.UNSIGNED,
				center: Sequelize.STRING,
				radius: Sequelize.INTEGER.UNSIGNED
				});
				
			Game.hasMany(User);
			User.hasMany(Game);
			
			Game.hasMany(Suggestion);
			Suggestion.belongsTo(Game);
			Game.belongsTo(Suggestion, { as: 'CurrentSuggestion', constraints: false } );
			
			Suggestion.belongsTo(User);
			User.hasMany(Suggestion);
			

				
			sequelize.sync({ force: true }).complete(function(err) { callback(null); } );
	
		},
		
		function (callback) {
		
		var testUser = User.build({ id: 'TESTID', name: 'john', wins: 2, points: 5, extras: 3 });
			
			testUser.save().complete(function(err) {
				if (!!err) {
						console.log('The instance has not been saved:', err);
						callback(err);
					} else {
						console.log('We have a persisted instance now');
						callback(null, testUser);
					}
				});
		
		},
		
		function (testUser, callback)
		{
			var testSuggestion = Suggestion.build({ name: "Moe's", location: "Atlanta", votes: 2 });
						
					testSuggestion.save().complete(function(err) {
						if (!!err) {
							console.log('The instance has not been saved:', err);
							callback(err);
						} else {
							console.log('We have a persisted instance now');
							
							testUser.addSuggestion(testSuggestion);
							
							//sequelize.sync().complete(function(err) { }  );

							/*
							createUser("ABCDE", "John Smith", function() { getUser("ABCDE", function(user)
								{ 	console.log("Here's the user we got.");
								  	console.log(user);
									createGame("dinner", 5, "ATLANTA", 20, user, function() { console.log("game created");  } ); 
															
									} );  } );
							*/
							
							
							createGame("dinner", 5, "Atlanta", 20, testUser, 
								function(game) {addInitialSuggestion("Jimmy Johns", "Atlanta", testUser, game, function(){ callback(null, testUser, game); } )} );
							
							}
						});
		},
		
		function (testUser, game, callback)
		{
			addInitialSuggestion("Subway", "Atlanta", testUser, game, function(){ callback(null, game); } );
		},
		
		function (game, callback)
		{
			//unrelated - no dependency
			
			getGameSuggestionHistory(game, function(suggestions) {console.log('*********SUGG HISTORY***********'); console.log(suggestions); });
			getCurrentSuggestion(game, function(currentSuggestion) {console.log('********CURRENT*********'); console.log(currentSuggestion); });
			
			createUser("ABCDE", "John Smith", function(user) {
				addUserToGame(game, user, function() { callback(null, user); } ); 
			}
			);
		
		},
		
		function (user, callback)
		{
			createGame("lunch", 3, "Sandy Springs", 10, user, function() 
			{ 
				getUserGames(user, function(games) {console.log('********USER ABCDE GAMES*********'); console.log(games);} );
			} ); 
		}
	],
	function(err, result) {
		
	});
	
}

function createUser (id, name, callback)
{
	User.create({id: id, name: name, wins: 0, points: 0, extras: 0}).then(function(user) {callback(user)} ); 
}

function getUser (id, callback)
{
	User.find(id).then(function(user){callback(user); } );
}

function createGame (eventType, suggestionTTL, center, radius, user, callback)
{
	Game.create({eventType: eventType, suggestionTTL: suggestionTTL, center: center, radius: radius})
	.then(function(game){
		//user.addGame(game).then(callback(game));	//doesn't work with promises
		user.addGame(game).then(function() {callback(game) });
	});
}

function getGame (id, callback)
{
	Game.find(id).then(function(game){callback(game); } );
}

function addSuggestion (name, location, user, game, suggestionCallback)
{
	async.waterfall([
	
		function(callback) {
			var suggestion = Suggestion.build({name: name, location: location, votes: 0});
			
			suggestion.save().complete(function(err) {
				if (!!err) {
						console.log('The instance has not been saved:', err);
						callback(err);
					} else {
						console.log('We have a persisted instance now');
						callback(null, suggestion);
					}
				});
		},
		
		function(suggestion, callback)
		{
			user.addSuggestion(suggestion).then(function() {callback(null, suggestion)});
		},
		
		function (suggestion, callback)
		{
			game.addSuggestion(suggestion).then(function() {callback(null, suggestion)});
		},
		
		function (suggestion, callback)
		{
			game.setCurrentSuggestion(suggestion).then(function() {callback(null, suggestion)});
		},
		
		function (suggestion, callback)
		{
			suggestionCallback(suggestion);
		}
	
	],
	function(err, result) {
		
	});
}

function addUserToGame (game, user, callback)
{
	game.addUser(user).then(callback());
}

function getGameSuggestionHistory (game, callback)
{
	game.getSuggestions().then(function(suggestions) {callback(suggestions);} );
}

function getUserGames (user, callback)	//not tested yet
{
	user.getGames().then(function(games) {callback(games);} );
}

function getCurrentSuggestion (game, callback)
{
	game.getCurrentSuggestion().then(function(currentSuggestion) {callback(currentSuggestion);} );
}



