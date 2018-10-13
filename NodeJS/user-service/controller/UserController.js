const User = require('../repository/user.model');

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
    .exec()
    .then(result => {
      res.status(200).json({
        message: result
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
      user: user,
      message: "Usuario Creado"
    });
  })
  .catch(err => {
    res.status(500).json({
      error: err
    });
  });
};

module.exports = {
  getAllUser,
  addUser,
  removeUser
};