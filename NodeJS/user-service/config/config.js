const dbSettings = {
  db: process.env.DB || 'test',
  user: process.env.DB_USER || 'search',
  pass: process.env.DB_PASS || 'password',
  server: process.env.DB_SERVER || 'localhost:27017'
};

// server parameters
const serverSettings = {
  port: process.env.PORT || 3000,
  loggerFormat : process.env.NODE_ENV === 'PRODUCTION' ? 'dev' : 'combined'
};

module.exports = {
  dbSettings,
  serverSettings
};