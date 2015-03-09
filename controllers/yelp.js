var OAuth = require('oauth-request');
//have to install this: npm i oauth-request --save

var search = 'https://api.yelp.com/v2/search?location=Atlanta&limit=5&term=food&sort=0';

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

//list user timeline
/*
yelp.get(search, function(err, res, feed) {
    console.log(feed);
});
*/

//list user timeline limit 5
yelp.get({
    url: search,
    qs: {
        count: 5
    },
    json: true
}, function(err, res, feed) {
    console.log(feed);
});