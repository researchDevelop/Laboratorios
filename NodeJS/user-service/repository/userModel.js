let mongoose = require('mongoose');
let bcrypt = require('bcrypt'),
  SALT_WORK_FACTOR = 10;

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

//el tercer parametro es el nombre de la collection mongo
module.exports = mongoose.model('Users', UserSchema, 'Users');