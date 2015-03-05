define({ "api": [
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
            "field": "name",
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