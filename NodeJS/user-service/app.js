let express = require('express');
let userRoute = require('./routes/userRouter');
let { serverSettings } = require('./config/config');
let logger = require('./config/logger');
const db = require('./db');
const bodyParser = require('body-parser');
let mongoose = require('mongoose');
let sessionConfig = require('./config/session');
mongoose.Promise = global.Promise;
const swaggerUi = require('swagger-ui-express');
const swaggerDocument = require('./api-docs.json');

let app = express();
sessionConfig(app);
app.use(bodyParser.json());

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
app.use((req, res, next) => {
  const error = new Error("Not found");
  error.status = 404;
  next(error);
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

function errorHandler(err, req, res, next) {
  res.status(500).json({
    message: 'Ruta Inexistente'
  });
}
app.use(errorHandler);

let server =  app.listen(serverSettings.port, () => {
  logger.info(`Servicio escuchando en el puerto : ${serverSettings.port}`);
});

//validamos la ejecucion, solo si existe conexion a BD, se puede conectar
let init = module.exports = db().then((db) => server).catch((err) => {
  logger.error(err.stack);
  process.exit(1);
});

module.exports = {server,init};