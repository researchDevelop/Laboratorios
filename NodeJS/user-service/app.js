let express = require('express');
let app = express();
let userRoute = require('./routes/userRouter');
let { serverSettings } = require('./config/config');
let logger = require('winston');
const db = require('./db');
const bodyParser = require('body-parser');
let mongoose = require('mongoose');
let morgan = require('morgan');
//middlewares
//logger

app.use(bodyParser.json());

//mapeamos el router
app.use(userRoute);

mongoose.Promise = global.Promise;

app.use(morgan("dev"));
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

app.use((error, req, res, next) => {
  res.status(error.status || 500);
  res.json({
    error: {
      message: error.message
    }
  });
});

//validamos la ejecucion, solo si existe conexion a BD, se puede conectar
db().then((db) => {
  app.listen(serverSettings.port, () => {
    logger.info(`Servicio escuchando en el puerto : ${serverSettings.port}`);
  });
}).catch((err) => {
  logger.error(err.stack);
  process.exit(1);
});
