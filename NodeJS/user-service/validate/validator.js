var Joi = require('joi');

const signInValidator = Joi.object().options({ abortEarly: false }).keys({
  email: Joi.string().email().required().label('User Email'),
  password: Joi.string().regex(/^[a-zA-Z0-9]{3,30}$/),
  passwordconfirmation: Joi.any().valid(Joi.ref('password')).required().options({ language: { any: { allowOnly: 'must match password' } } }).label('Password Confirmation'),
  firstname: Joi.string().required(),
  lastname: Joi.string().required(),
  age: Joi.number().min(18).required(),
  username: Joi.string().alphanum().min(3).max(30).required(),
});

module.exports = signInValidator;