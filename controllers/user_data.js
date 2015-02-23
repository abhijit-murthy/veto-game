'use strict';

var restify = require('restify');

function createUser(req,res,next){
	return next(new restify.InvalidArgumentError("Test Method"));
}
exports.createUser = createUser;

function getUserData(req,res,next){
	return next(new restify.InvalidArgumentError("Test Method"));
}
exports.getUserData = getUserData;
