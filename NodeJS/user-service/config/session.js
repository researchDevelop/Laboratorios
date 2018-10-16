const session = require('express-session');
const uuid = require('uuid/v4');

// add & configure middleware
let sessionConfig = (app) => app.use(session({
  secret: 'work hard',
  resave: true,
  saveUninitialized: false
}));

//use sessions for tracking logins
app.use(session({
  
}));

module.exports = sessionConfig;