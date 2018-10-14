const mongoose = require('mongoose');
let { dbSettings } = require('./config/config');
let logger = require('./config/logger');

mongoose.set("debug", (collectionName, method, query, doc) => {
  logger.info(`${collectionName}.${method}`, JSON.stringify(query), doc);
});

module.exports = function () {
  return new Promise((resolve, reject) => {
    mongoose.connect(`mongodb://${dbSettings.user}:${dbSettings.pass}@${dbSettings.server}/${dbSettings.db}`, { useNewUrlParser: true, useCreateIndex: true })
    .then((bd) => {
      resolve(bd);
    })
    .catch((err) => {
      reject(err);
    });
  });
};