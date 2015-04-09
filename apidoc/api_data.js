define({ "api": [
  {
    "type": "post",
    "url": "/game_data/add_user_to_game",
    "title": "Add a given user to a Game",
    "name": "AddUserToGame",
    "group": "Game",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "user_id",
            "description": "<p>Creating User&#39;s id</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "game_id",
            "description": "<p>Target Game id</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "user_id",
            "description": "<p>Creating User&#39;s id</p> "
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "game_id",
            "description": "<p>Target Game id</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad Game/User id, Could not add user to game</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controllers/game_data.js",
    "groupTitle": "Game"
  },
  {
    "type": "post",
    "url": "/game_data/create",
    "title": "Create a new Game",
    "description": "<p>Creates a new Game and adds the creating User to it.</p> ",
    "name": "CreateGame",
    "group": "Game",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "user_id",
            "description": "<p>Creating User&#39;s Facebook ID</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "event_type",
            "description": "<p>Type of event. Must be one of &#39;dinner&#39;,&#39;lunch, or &#39;breakfast&#39;</p> "
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "suggestion_ttl",
            "description": "<p>Time to Live of a suggestion before the game ends (in minutes)</p> "
          },
          {
            "group": "Parameter",
            "type": "Location",
            "optional": false,
            "field": "center",
            "description": "<p>Center of the circle in which we are filtering suggestions</p> "
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "radius",
            "description": "<p>Radius of the circle in which we are filtering suggestions</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Name of the Game being created</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "event_time",
            "description": "<p>Time of the event</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "time_ending",
            "description": "<p>Ending time of the Game</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Integer",
            "optional": false,
            "field": "game_id",
            "description": "<p>The ID of the newly created game</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad User ID</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controllers/game_data.js",
    "groupTitle": "Game"
  },
  {
    "type": "get",
    "url": "/game_data/:id",
    "title": "Get Game information",
    "description": "<p>Get detailed Game information</p> ",
    "name": "GetGameData",
    "group": "Game",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Game ID</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Creating User&#39;s Facebook ID</p> "
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "event_type",
            "description": "<p>Type of event. Must be one of &#39;dinner&#39;,&#39;lunch, or &#39;breakfast&#39;</p> "
          },
          {
            "group": "Success 200",
            "type": "DateTime",
            "optional": false,
            "field": "eventTime",
            "description": "<p>The time of the event</p> "
          },
          {
            "group": "Success 200",
            "type": "DateTime",
            "optional": false,
            "field": "timeEnding",
            "description": "<p>Time the game ends</p> "
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "suggestion_ttl",
            "description": "<p>Time to Live of a suggestion before the game ends (in minutes)</p> "
          },
          {
            "group": "Success 200",
            "type": "Location",
            "optional": false,
            "field": "center",
            "description": "<p>Center of the circle in which we are filtering suggestions</p> "
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "radius",
            "description": "<p>Radius of the circle in which we are filtering suggestions</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad Game ID</p> "
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "GameFinished",
            "description": "<p>Game is finished</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "GameFinished:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"code\": \"GameFinished\",\n\t\"message\": \"Game has ended\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "controllers/game_data.js",
    "groupTitle": "Game"
  },
  {
    "type": "get",
    "url": "/game_data/get_user_games/:id",
    "title": "Get User's Games",
    "description": "<p>Get all the Games that a given User is associated with</p> ",
    "name": "GetUserGames",
    "group": "Game",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>The requesting User&#39;s ID</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Array",
            "optional": false,
            "field": "Games",
            "description": "<p>that the User is a part of</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad User ID/Could not retrieve list of games</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controllers/game_data.js",
    "groupTitle": "Game"
  },
  {
    "type": "post",
    "url": "/suggestion_data/create",
    "title": "Create a new Suggestion",
    "description": "<p>Creates a new Suggestion for a given User and adds it to a given Game as the current Suggestion.</p> ",
    "name": "CreateSuggestion",
    "group": "Suggestion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "user_id",
            "description": "<p>Creating User&#39;s Facebook ID</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "game_id",
            "description": "<p>Game to add to</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Name of the location</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "location",
            "description": "<p>Location of the suggestion</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>The ID of the newly created suggestion</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad User ID</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controllers/suggestion_data.js",
    "groupTitle": "Suggestion"
  },
  {
    "type": "get",
    "url": "/suggestion_data/current_suggestion/:id",
    "title": "Get current Suggestion",
    "description": "<p>Gets the current Suggestion in an ongoing Game</p> ",
    "name": "GetCurrentSuggestion",
    "group": "Suggestion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>The Game ID</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Suggestion",
            "optional": false,
            "field": "Unamed",
            "description": "<p>The current Suggestion</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad Game Id</p> "
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "GameFinished",
            "description": "<p>Game is finished</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "GameFinished:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"code\": \"GameFinished\",\n\t\"message\": \"Game has ended\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "controllers/suggestion_data.js",
    "groupTitle": "Suggestion"
  },
  {
    "type": "get",
    "url": "/suggestion_data/game_history/:id",
    "title": "Get Suggestion history",
    "description": "<p>Gets the Suggestion history for an ongoing Game</p> ",
    "name": "GetGameSuggestionHistory",
    "group": "Suggestion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>The Game ID</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Array",
            "optional": false,
            "field": "Unamed",
            "description": "<p>An array of Suggestions</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad Game Id</p> "
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "GameFinished",
            "description": "<p>Game is finished</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "GameFinished:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"code\": \"GameFinished\",\n\t\"message\": \"Game has ended\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "controllers/suggestion_data.js",
    "groupTitle": "Suggestion"
  },
  {
    "type": "get",
    "url": "Get",
    "title": "Suggestions from Yelp Api",
    "description": "<p>Gets Suggestions from the Yelp API using information about the Game.</p> ",
    "name": "GetYelpSuggestions",
    "group": "Suggestion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "game_id",
            "description": "<p>Game to get information from</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "optional": false,
            "field": "Suggestions",
            "description": "<p>from Yelp</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad Game Id</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controllers/suggestion_data.js",
    "groupTitle": "Suggestion"
  },
  {
    "type": "post",
    "url": "/suggestion_data/upvote",
    "title": "Upvote",
    "description": "<p>Upvotes a game&#39;s current suggestion (provided it matches the suggestion parameter)</p> ",
    "name": "Upvote",
    "group": "Suggestion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "game_id",
            "description": "<p>Game ID</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "suggestion_id",
            "description": "<p>Current Suggestion ID</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Integer",
            "optional": false,
            "field": "count",
            "description": "<p>Current number of upvotes</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad Game ID</p> "
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "GameFinished",
            "description": "<p>Game is finished</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "GameFinished:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"code\": \"GameFinished\",\n\t\"message\": \"Game has ended\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "controllers/suggestion_data.js",
    "groupTitle": "Suggestion"
  },
  {
    "type": "post",
    "url": "/suggestion_data/veto",
    "title": "Veto",
    "description": "<p>Veto a game&#39;s current suggestion and make a new suggestion</p> ",
    "name": "Veto___Suggest_New",
    "group": "Suggestion",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "game_id",
            "description": "<p>Game ID</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "user_id",
            "description": "<p>User ID</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "curr_suggestion_id",
            "description": "<p>Current Suggestion ID</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "new_suggestion_name",
            "description": "<p>New Suggestion Name</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "new_suggestion_loc",
            "description": "<p>New Suggestion Location</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>ID of new suggestion</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Bad Game ID</p> "
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "GameFinished",
            "description": "<p>Game is finished</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "GameFinished:",
          "content": "HTTP/1.1 200 OK\n{\n\t\"code\": \"GameFinished\",\n\t\"message\": \"Game has ended\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "controllers/suggestion_data.js",
    "groupTitle": "Suggestion"
  },
  {
    "type": "post",
    "url": "/user_data/create",
    "title": "Create a new User",
    "name": "CreateUser",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "user_id",
            "description": "<p>Users Facebook ID (remember to get this from the Facebook API)</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "user_name",
            "description": "<p>Users Name</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "optional": false,
            "field": "Just",
            "description": "<p>the HTTP 200 Response</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>User already exists</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controllers/user_data.js",
    "groupTitle": "User"
  },
  {
    "type": "get",
    "url": "/user_data/:id",
    "title": "Get User information",
    "name": "GetUserData",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Users Facebook ID</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Users Facebook ID</p> "
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Users Name</p> "
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "wins",
            "description": "<p>Number of games the User has won</p> "
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "points",
            "description": "<p>Number of points the User has accumulated</p> "
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "extras",
            "description": "<p>Number of extra vetoes they have accumulated</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "InvalidArgumentError",
            "description": "<p>Thrown when the user queried does not exist</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controllers/user_data.js",
    "groupTitle": "User"
  }
] });