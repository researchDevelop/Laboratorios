let mongoose = require('mongoose');

let UserSchema = mongoose.Schema({
  firstName: String,
  lastName: String,
  age:Number,
  email: {
    type: String,
    require: true,
    unique:true
  }

},{
  versionKey: false // You should be aware of the outcome after set to false
});
//el tercer parametro es el nombre de la collection mongo
module.exports = mongoose.model('Users', UserSchema, 'Users');