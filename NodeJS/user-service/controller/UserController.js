const User = require('../repository/UserSchema');

let getAllUser = (req, res, next) => {
  User.find()
    .select(["-_id"])
    .exec()
    .then(docs => {
      res.status(200).json(docs);
    })
    .catch(err => {
      res.status(err.status).json({
        error: err.message
      });
    });
};

let removeUser = (req, res, next) => {
  User.deleteOne({ _id: req.params.userId })
    .then(result => {
      res.status(200).json({
        message: "Usuario Eliminado"
      });
    })
    .catch(err => {
      res.status(err.status).json({
        error: err.message
      });
    });
};

let removeByUsername = (req, res, next) => {
  User.deleteOne({ username: req.params.username })
    .then(result => {
      res.status(200).json({
        message: "Usuario Eliminado"
      });
    })
    .catch(err => {
      res.status(err.status).json({
        error: err.message
      });
    });
};

let addUser = (req, res, next) => {
  const user = new User(req.body);
  user.save().then(user => {
    res.status(201).json({
      message: "Usuario Creado",
      User: user
    });
  })
  .catch(err => {
    res.status(err.status).json({
      error: err.message
    });
  });
};

let updateUser = (req, res, next) => {
  const id = req.params.userId;
  const updateOps = {};
  for (const ops of req.body) {
    updateOps[ops.propName] = ops.value;
  }
  User.update({ _id: id }, { $set: updateOps })
    .exec()
    .then(result => {
      res.status(200).json({
        message: "Usuario Actualizado"
      });
    })
    .catch(err => {
      res.status(err.status).json({
        error: err.message
      });
    });
};

module.exports = {
  getAllUser,
  addUser,
  updateUser,
  removeUser,
  removeByUsername
};