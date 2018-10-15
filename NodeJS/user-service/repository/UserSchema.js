let mongoose = require('mongoose');
let logger = require('../config/logger');
let bcrypt = require('bcrypt'),
  SALT_WORK_FACTOR = 10;
let {ErrorMessage} = require('../model/error');



let UserSchema = mongoose.Schema({
  lastname: String,
  firstname: String,
  age: Number,
  email: {
    type: String,
    require: true,
    unique: true
  },
  username: {
    type: String,
    require: true,
    unique: true,
    trim: true
  },
  password: {
    type: String,
    require: true
  }
}, {
    emitIndexErrors: true,
    versionKey: false
  });

UserSchema.pre('save', function (next) {
  var user = this;
  if (!user.isModified('password')) return next();
  bcrypt.genSalt(SALT_WORK_FACTOR, function (err, salt) {
    if (err) return next(err);
    bcrypt.hash(user.password, salt, function (err, hash) {
      if (err) return next(err);
      user.password = hash;
      next();
    });
  });
});

UserSchema.methods.comparePassword = function (candidatePassword, cb) {
  bcrypt.compare(candidatePassword, this.password, function (err, isMatch) {
    if (err) return cb(err);
    cb(null, isMatch);
  });
};
var handleE11000 = function (error, res, next) {
  if (error.name === 'MongoError' && error.code === 11000) {
    console.log(error);
    next(new ErrorMessage(409, 'There was a duplicate key error'));
  } else {
    next();
  }
};

UserSchema.post('save', handleE11000);
UserSchema.post('update', handleE11000);
UserSchema.post('findOneAndUpdate', handleE11000);
UserSchema.post('insertMany', handleE11000);

//el tercer parametro es el nombre de la collection mongo
module.exports = mongoose.model('Users', UserSchema, 'Users');