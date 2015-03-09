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
				radius: Sequelize.INTEGER.UNSIGNED,
				finished: Sequelize.BOOLEAN
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
							
							
							createGame("dinner", 5, "Atlanta", 20, testUser).then(function(game) { 
								addSuggestion("Jimmy Johns", "Atlanta", testUser, game).then(function(suggestion)  {
									console.log("*!*!*!*!*!*!*!*!*"); callback(null, testUser, game); } );
								} ); 
							
							}
						});
		},
		
		function (testUser, game, callback)
		{
			addSuggestion("Subway", "Atlanta", testUser, game)
			.then(function(suggestion){
				console.log(suggestion);
			});
		},
		
		function (game, callback)
		{
			
			getGameSuggestionHistory(game).then(function(suggestions){console.log('*********SUGG HISTORY***********'); console.log(suggestions); } );
			getCurrentSuggestion(game).then(function(currentSuggestion) {console.log('********CURRENT*********'); console.log(currentSuggestion);} );
			

			
			createUser("ABCDE", "John Smith").then(function(user) { 
				addUserToGame(game, user).then( function() {callback(null, user);} ); 
				} );
		
		},
		
		function (user, callback)
		{
			createGame("lunch", 3, "Sandy Springs", 10, user).then(function(game) {
				getUserGames(user).then(function(games) {console.log('********USER ABCDE GAMES*********'); console.log(games);} );
				} );
		}
	],
	function(err, result) {
		
	});
	
}

function createUser (id, name)
{
	return User.create({id: id, name: name, wins: 0, points: 0, extras: 0}); 
}

function getUser (id)
{
	return User.find(id);
}

function createGame (eventType, suggestionTTL, center, radius, user)
{
	return Game.create({eventType: eventType, suggestionTTL: suggestionTTL, center: center, radius: radius, finished: false})
	.then(function(game){
		return user.addGame(game);
	});
}

function getGame (id)
{
	return Game.find(id);
}

function addSuggestion (name, location, user, game)
{
	return Suggestion.build({name: name, location: location, votes: 0})

	.save()

	.then(
		function(suggestion){
			user.addSuggestion(suggestion);
			return suggestion;
		}
	).then(
		function(suggestion){
			game.addSuggestion(suggestion);
			return suggestion; 
		}
	).then(
		function(suggestion){
			game.setCurrentSuggestion(suggestion);
			return suggestion; 
		}
	);
}

function addUserToGame (game, user)
{
	return game.addUser(user);
}

function getGameSuggestionHistory (game)
{
	return game.getSuggestions();
}

function getUserGames (user)
{
	return user.getGames();
}

function getCurrentSuggestion (game)
{
	return game.getCurrentSuggestion();
}

function getUsers ()
{
	return User.findAll();
}

function getPastGames (user)
{
	//return Game.findAll({ where: {finished: true, } });
	return user.getGames({where: {finished:true}});
}

function getCurrentGames (user)
{
	return user.getGames({where: {finished:false}});
}



