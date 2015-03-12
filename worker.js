/*global module:true, require:true, console:true, process:true */

'use strict';

var path = require('path')
  , restify = require('restify')
  , gameDataController = require('./controllers/game_data')
  , userDataController = require('./controllers/user_data')
  , suggestionDataController = require('./controllers/suggestion_data');


exports.createServer = createServer;


/*
 * Set up server
 * @return the created server
 */
function createServer (logger) {

  var config = {
    name: require(path.join(__dirname, 'package')).name
  };

  if (logger) config.log = logger;

  var server = restify.createServer(config);

  server.use(restify.acceptParser(server.acceptable));
  server.use(restify.bodyParser());
  server.use(restify.queryParser());

  server.on('NotFound', function (req, res, next) {
    if (logger) logger.debug('404', 'Request for ' + req.url + ' not found. No route.');
    res.send(404, req.url + ' was not found');
  });
  
  if (logger) server.on('after', restify.auditLogger({ log: logger }));
  
  // DEFINE ROUTES
  
  // sample route
  // USAGE EXAMPLE: /test
  server.get('/test', function (req, res, next) {
    res.send({'result': 'test'});      
    return next();
  });
  //User data endpoints
  server.post(userDataController.createUserEndpoint, userDataController.createUser);
  server.get(userDataController.getUserDataEndpoint, userDataController.getUserData);

  //Game data endpoints
  server.get(gameDataController.getGameDataEndpoint,gameDataController.getGameData);
  server.post(gameDataController.createGameEndpoint,gameDataController.createGame);
  server.post(gameDataController.addUserToGameEndpoint,gameDataController.addUserToGame);
  server.get(gameDataController.getUserGamesEndpoint,gameDataController.getUserGames);

  //Suggestion data endpoints
  server.post(suggestionDataController.createSuggestionEndpoint,suggestionDataController.createSuggestion);
  server.get(suggestionDataController.getGameSuggestionHistoryEndpoint,suggestionDataController.getGameSuggestionHistory);
  server.get(suggestionDataController.getCurrentSuggestionEndpoint,suggestionDataController.getCurrentSuggestion);
  server.get(suggestionDataController.getYelpSuggestionsEndpoint, suggestionDataController.getYelpSuggestions);
  server.get(/\/?.*/, restify.serveStatic({
	default: 'index.html',
	directory: './apidoc'
	}));
  
  return server;
}
