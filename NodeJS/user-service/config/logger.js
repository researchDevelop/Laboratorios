const winston = require('winston');

// const logger = module.exports = winston.createLogger({
//   transports: [new winston.transports.Console()],
//   format: winston.format.combine(
//     winston.format.colorize({ all: true }),
//     winston.format.simple()
//   )
// });

// const logger = module.exports = winston.createLogger({
//   format: winston.format.printf(info => {
//     return JSON.stringify(info)
//       .replace(/\{/g, '< wow ')
//       .replace(/\:/g, ' such ')
//       .replace(/\}/g, ' >')
//   }),
//   transports: [
//     new winston.transports.Console(),
//   ]
// });

// const logger = module.exports = winston.createLogger({
//   transports: [new winston.transports.Console()],
//   format: winston.format.combine(
//     winston.format(function dynamicContent(info, opts) {
//       info.message = '[dynamic content] ' + info.message;
//       return info;
//     })(),
//     winston.format.simple()
//   )
// });

const fs = require('fs');
const { createLogger, format, transports } = winston;

const logger = module.exports = createLogger({
  format: format.combine(
    format.timestamp(),
    format.simple()
  ),
  transports: [
    new transports.Console({
      format: format.combine(
        format.timestamp(),
        format.colorize(),
        format.simple()
      )
    }),
    new transports.Stream({
      stream: fs.createWriteStream('./logs/logger.log')
    })
  ]
})