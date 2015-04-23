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
exports.getSuggestion = getSuggestion;
exports.addSuggestion = addSuggestion;
exports.addUserToGame = addUserToGame;
exports.getGameSuggestionHistory = getGameSuggestionHistory;
exports.getUserGames = getUserGames;
exports.getCurrentSuggestion = getCurrentSuggestion;
exports.isGameFinished = isGameFinished;
exports.upvote = upvote;
exports.veto = veto;
exports.getCurrentGames = getCurrentGames;
exports.getPastGames = getPastGames;
exports.getGameUsers = getGameUsers;

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
				votes: Sequelize.INTEGER.UNSIGNED,
				mobile_url: Sequelize.STRING,
				image_url: Sequelize.STRING,
				rating_url: Sequelize.STRING
				});
				
			Game = sequelize.define('Game', {
				name: Sequelize.STRING,
				eventTime: Sequelize.DATE,
				eventType: Sequelize.STRING,
				timeEnding: Sequelize.DATE,
				suggestionTTL: Sequelize.INTEGER.UNSIGNED,
				center: Sequelize.STRING,
				radius: Sequelize.INTEGER.UNSIGNED,
				finished: Sequelize.BOOLEAN
				});
				
			Game.belongsToMany(User,{through:"GamesUsers"});
			User.belongsToMany(Game,{through:"GamesUsers"});
			
			Game.hasMany(Suggestion);
			Suggestion.belongsTo(Game);
			Game.belongsTo(Suggestion, { as: 'CurrentSuggestion', constraints: false } );
			
			Suggestion.belongsTo(User);
			User.hasMany(Suggestion);
			

				
			sequelize.sync({ force: false }).complete(function(err) { callback(null); } );
	
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
					
					var date1 = new Date();
					date1.setHours(date1.getHours() + 5);
					
					createGame("GameA", "dinner", 5, "Atlanta", 20, testUser, date1, date1).then(function(game) {
						addSuggestion("Jimmy Johns", "Atlanta", testUser, game);
						return game;
					}).then(function(game){
						callback(null,testUser,game);
					});
				}
			});
		},
		
		function (testUser, game, callback)
		{
			addSuggestion("Subway", "Atlanta", testUser, game)
			.then(function(suggestion){
				console.log(suggestion);
				callback(null, game, testUser);
			});
		},
		
		function (game, user, callback)
		{
			
			isGameFinished(game).then(function(finished) { console.log("********************FINISHED???******************"); console.log(finished); } );
			getGameSuggestionHistory(game).then(function(suggestions){ console.log('*********SUGG HISTORY***********'); console.log(suggestions); } );
			getCurrentSuggestion(game).then(function(currentSuggestion) { 
				console.log('********CURRENT*********');
				console.log(currentSuggestion);
				//upvote(game, currentSuggestion);
				//vetoAndSuggest(game, user, currentSuggestion, "The Varsity", "Atlanta");
				veto(game, user, currentSuggestion, "The Varsity", "Atlanta").then(function(result) {console.log("****************VETO/SUGGEST******************"); console.log(result); } );
				} );
			


			createUser("JKL", "Carl Rogers").then(function(user) { 
				addUserToGame(game, user).then( function() { } ); 
				} );
				
			createUser("GHI", "Marco Rodriguez").then(function(user) { 
				addUserToGame(game, user).then( function() { } ); 
				} );

			createUser("DEF", "Sally Johnson").then(function(user) { 
				addUserToGame(game, user).then( function() { } ); 
				} );
			
			createUser("ABC", "John Smith").then(function(user) { 
				addUserToGame(game, user).then( function() {callback(null, user);} ); 
				} );
		
		},
		
		function (user, callback)
		{
			var date2 = new Date();
				date2.setHours(date2.getHours() + 8);			
			
			createGame("GameB", "lunch", 3, "Sandy Springs", 10, user, date2, date2).then(function(game) {
				getUserGames(user).then(function(games) {console.log('********USER ABCDE GAMES*********'); console.log(games); } );
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

function createGame (name, eventType, suggestionTTL, center, radius, user, eventTime, timeEnding)
{
	return Game.create({name: name, eventType: eventType, suggestionTTL: suggestionTTL, center: center, radius: radius, finished: false, eventTime: eventTime, timeEnding: timeEnding})
	.then(function(game){
		// user.addGame(game);
		game.addUser(user);
		return game;
	});
}

function getGame (id)
{
	return Game.find(id);
}

function getSuggestion (id)
{
	return Suggestion.find(id);
}

function addSuggestion (name, location, mobile_url, image_url, rating_url, user, game)
{
	return Suggestion.build({
		name: name, 
		location: location, 
		votes: 0, 
		mobile_url: mobile_url,
		image_url: image_url,
		rating_url: rating_url
	})

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

function isGameFinished (game)
{
	//if finished flag is true, return true
	//else, check for
	//1) current suggestion TTL expiry
	//2) game reaches end time
	//3) unanimous upvotes for current suggestion
	//if any, set finished to true and return true
	//else return false
	
	return new sequelize.Promise (function(fulfill, reject){
		
		if (game.finished == true)
		{
			console.log("Finished flag was true");
			//return true;
			fulfill(true);
		}
		else
		{
			var ttl = game.suggestionTTL;
		
			getCurrentSuggestion(game)
			.then(
				function(suggestion){	
				
						if (suggestion == null)
						{
							game.finished = true;
							game.save().then(function() { } );
							
							return sequelize.Promise.reject("Current suggestion is null");
						}
				
						var suggestionExpiration = new Date(suggestion.createdAt);
						suggestionExpiration.setMinutes(suggestionExpiration.getMinutes() + ttl);
						var now = new Date();
					 
						if (now >= suggestionExpiration)
						{
							console.log ("Suggestion TTL has expired");
							console.log ("Suggestion created at " + suggestion.createdAt);
							console.log ("Suggestion TTL is " + ttl);
							console.log ("Suggestion expires at " + suggestionExpiration);
							console.log ("Now it is " + now);
							
							//set finished to true
							game.finished = true;
							game.save().then(function() { } );
							fulfill(true);					
						}
						else if (now >= new Date(game.timeEnding))
						{
						
							console.log ("Game ending time reached");
							console.log ("Game ends at " + game.timeEnding);
							console.log ("Now it is " + now);						
						
							game.finished = true;
							game.save().then(function() { } );	
							fulfill(true);	
						}
					
					return suggestion;
					}
				)
				.then(function(suggestion) {
					game.getUsers().then(function(users) { 
					
							if (suggestion.votes >= (users.count - 1))   
							{
								game.finished = true;
								game.save().then(function() { } );	
								fulfill(true);	
							}
							else
							{
								fulfill(false);
							}
							
								});					
					
					})
					.error(function(err) { console.log(err); fulfill(true); });
		}
		
		});

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
	//return user.getGames();
	
	return user.getGames().reduce(function(total,game){
		return isGameFinished(game)
				.then(
					function(isGameFinished){

						return getCurrentSuggestion(game)
							.then(
								function(suggestion){
									game.dataValues.currentSuggestion = suggestion;
									
									return getGameUsers(game)
										.then(
											function (users){
												if (users != null)
												{
													game.dataValues.userCount = users.length;
													
												}
												total.push(game);
												return total;
												
											});
									
									
								});				
						
						
					});
	},[]);
}

function getCurrentSuggestion (game)
{
	return game.getCurrentSuggestion();
}

function getUsers ()
{
	return User.findAll();
}

function veto (game, user, suggestionToVeto, newSuggestionName, newSuggestionLocation,newSuggestionMobileUrl,newSuggestionImageUrl,newSuggestionRatingUrl)
{
	return new sequelize.Promise (function(fulfill, reject){
	
	getCurrentSuggestion(game).then(function(suggestion) { 
		if (suggestionToVeto.id == suggestion.id) 
		{
			addSuggestion(newSuggestionName, newSuggestionLocation, newSuggestionMobileUrl,newSuggestionImageUrl,newSuggestionRatingUrl,user, game).then(function(suggestion) { fulfill(suggestion.values.id); } );
		}
		else
		{
			reject(-1);
		}
		
		});
		
	});
}

function upvote (game, suggestionToUpvote)
{
	return new sequelize.Promise (function(fulfill, reject){
	
	getCurrentSuggestion(game).then(function(suggestion) {
		if (suggestionToUpvote.id == suggestion.id)
		{
			suggestion.votes++;
			suggestion.save().then(function() { fulfill(suggestion.votes); } );
		}
		else
		{
			reject(-1);
		}
	});
	
	});
}

/*
function getPastGames (user)
{
	//return Game.findAll({ where: {finished: true, } });
	// return user.getGames({where: {finished:true}});

	return user.getGames().reduce(function(total,game){
		return isGameFinished(game)
				.then(
					function(isGameFinished){
						if(isGameFinished)
						{
							game.finished = true;
							total.push(game);
						}
						return total;
					});
	},[]);
}
*/

function getCurrentGames (user)
{
	return user.getGames().reduce(function(total,game){
		return isGameFinished(game)
				.then(
					function(isGameFinished){
						if(!isGameFinished)
						{
							return getCurrentSuggestion(game)
								.then(
									function(suggestion) {
										game.dataValues.currentSuggestion = suggestion;

										return getGameUsers(game)
											.then(
												function(users) {
													if (users != null)
														game.dataValues.userCount = users.length;
													total.push(game);
													return total;
													
													});								
										
										
										});					
							
						}
						else
						{
							return total;
						}
						//return total;
						
						
					});
	},[]);
}

function getPastGames (user)
{
	return user.getGames().reduce(function(total,game){
		return isGameFinished(game)
				.then(
					function(isGameFinished){
						if(isGameFinished)
						{
							return getCurrentSuggestion(game)
								.then(
									function(suggestion) {
										game.dataValues.currentSuggestion = suggestion;

										return getGameUsers(game)
											.then(
												function(users) {
													if (users != null)
														game.dataValues.userCount = users.length;
													total.push(game);
													return total;
													
													});								
										
										
										});					
							
						}
						else
						{
							return total;
						}
						//return total;
						
						
					});
	},[]);
}

function getGameUsers(game){
	return game.getUsers();
}


