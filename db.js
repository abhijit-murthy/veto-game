/*global module:true, require:true, console:true, process:true */

'use strict';

var fs = require('fs');
var Sequelize = require('sequelize');
var sequelize;
var async = require('async');

var credentials_filename = "secure_this_file.txt";
var db_name = "test";
var mysql_port = 3306;

exports.initDB = initDB;

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
		
			var User = sequelize.define('User', {
				  id: {
					type: Sequelize.STRING,
					primaryKey: true
				  },
                  name: Sequelize.STRING,
                  wins: Sequelize.INTEGER.UNSIGNED,
				  points: Sequelize.INTEGER.UNSIGNED,
				  extras: Sequelize.INTEGER.UNSIGNED
                });
				
			var Suggestion = sequelize.define('Suggestion', {
				name: Sequelize.STRING,
				location: Sequelize.STRING,
				votes: Sequelize.INTEGER.UNSIGNED
				});
				
			var Game = sequelize.define('Game', {
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
			
			Suggestion.belongsTo(User);
			User.hasMany(Suggestion);
			

				
			sequelize.sync({ force: true }).complete(function(err) {

			var testUser = User.build({ id: 'TESTID', name: 'john', wins: 2, points: 5, extras: 3 });
			
			testUser.save().complete(function(err) {
				if (!!err) {
					console.log('The instance has not been saved:', err);
					} else {
					console.log('We have a persisted instance now');
						
					var testSuggestion = Suggestion.build({ name: "Moe's", location: "Atlanta", votes: 2 });
						
					testSuggestion.save().complete(function(err) {
						if (!!err) {
							console.log('The instance has not been saved:', err);
						} else {
							console.log('We have a persisted instance now');
							
							testUser.addSuggestion(testSuggestion);
							//testSuggestion.setUser(testUser);
							
							sequelize.sync().complete(function(err) { }  );
						}
						});
					
					}
				});

			});
		
		}
	],
	function(err, result) {
		
	});
	
}
