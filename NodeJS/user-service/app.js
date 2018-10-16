let express = require('express');
let userRoute = require('./routes/userRouter');
let { serverSettings, loggerFormat } = require('./config/config');
const db = require('./db');
const bodyParser = require('body-parser');
let sessionConfig = require('./config/session');
const swaggerUi = require('swagger-ui-express');
const swaggerDocument = require('./api-docs.json');
const winston = require('winston');
const expressWinston = require('express-winston');
const morgan = require('morgan');
const addRequestId = require('express-request-id')();
const logger = require('./config/logger');


let app = express();
sessionConfig(app);
app.use(bodyParser.json());


app.use(addRequestId);
morgan.token('id', function getId(req) {
  return req.id
});

app.use(morgan(loggerFormat, {
  skip: function (req, res) {
      return res.statusCode < 400
  },
  stream: process.stderr
}));

app.use(morgan(loggerFormat, {
  skip: function (req, res) {
      return res.statusCode >= 400
  },
  stream: process.stdout
}));

app.use(function (req, res, next) {
  function afterResponse() {
      res.removeListener('finish', afterResponse);
      res.removeListener('close', afterResponse);
      var log = logger.loggerInstance.child({
          id: req.id
      }, true)
      log.info({res:res}, 'response')
  }
  res.on('finish', afterResponse);
  res.on('close', afterResponse);
  next();
});

//swagger doc
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));
//mapeamos el router
app.use('/v1',userRoute);

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept, Authorization"
  );
  if (req.method === "OPTIONS") {
    res.header("Access-Control-Allow-Methods", "PUT, POST, PATCH, DELETE, GET");
    return res.status(200).json({});
  }
  next();
});

app.use((err, req, res, next) => {
  if (err.error.isJoi) {
    res.status(400).json({
      type: err.type,
      message: err.error.toString()
    });
  } else {
    next(err);
  }
});
app.use(function(req,res){
  res.status(404).json('Something broke!');
});
app.use(function(err, req, res, next) {
  logger.logResponse(err.stack);
  res.status(500).json('Something broke!');
});

let server =  app.listen(serverSettings.port, () => {
  logger.logResponse(`Servicio escuchando en el puerto : ${serverSettings.port}`);
});

//validamos la ejecucion, solo si existe conexion a BD, se puede conectar
let init = module.exports = db().then((db) => server).catch((err) => {
  logger.logResponse(err.stack);
  process.exit(1);
});

module.exports = {server,init};