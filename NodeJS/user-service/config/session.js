const session = require('express-session');
const uuid = require('uuid/v4');
let logger = require('./logger');
const FileStore = require('session-file-store')(session);
// add & configure middleware
let sessionConfig = (app) => app.use(session({
  genid: (req) => {
    logger.info('Inside the session middleware');
    logger.info(req.sessionID);
    return uuid();
  },
  store: new FileStore(),
  secret: 'keyboard cat',
  resave: false,
  saveUninitialized: true
}));

module.exports = sessionConfig;