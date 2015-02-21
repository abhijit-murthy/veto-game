'use strict';

var restify = require('restify');

function createGame(req,res,next){
	return next(new restify.InvalidArgumentError("Stub Method"));
}
exports.createGame = createGame;

function getGameData(req,res,next){
	return next(new restify.InvalidArgumentError("Stub Method"));
}
exports.getGameData = getGameData;
