// simple configuration file

// database parameters
const dbSettings = {
    db: process.env.DB || 'test',
    user: process.env.DB_USER || 'search',
    pass: process.env.DB_PASS || 'password',
    servers: (process.env.DB_SERVERS) ? process.env.DB_SERVERS.split(' ') : [
      'localhost:27017'
    ],
    serverParameters: () => ({
      autoReconnect: true,
      poolSize: 10,
      socketoptions: {
        keepAlive: 300,
        connectTimeoutMS: 30000,
        socketTimeoutMS: 30000
      }
    })
  }
  
  // server parameters
  const serverSettings = {
    port: process.env.PORT || 3000
  }
  
  module.exports = Object.assign({}, { dbSettings, serverSettings })