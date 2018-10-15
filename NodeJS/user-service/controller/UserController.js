const User = require('../repository/UserSchema');

let getAllUser = (req, res, next) => {
  User.find()
    .select(["-_id"])
    .exec()
    .then(docs => {
      res.status(200).json(docs);
    })
    .catch(err => {
      res.status(500).json({
        error: err
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
      res.status(500).json({
        error: err
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
    console.log(err)
    res.status(500).json({
      error: err
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
      console.log(err);
      res.status(500).json({
        error: err
      });
    });
};

module.exports = {
  getAllUser,
  addUser,
  updateUser,
  removeUser
};