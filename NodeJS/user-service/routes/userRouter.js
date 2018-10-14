const express = require('express');
let router = express.Router();
let signInValidator = require('../validate/validator');
let UserController = require('../controller/UserController');
const validator = require('express-joi-validation')({
  passError: true 
});

router.get('/user', UserController.getAllUser);
router.post('/user',validator.body(signInValidator),UserController.addUser);
router.delete('/user/:userId',UserController.removeUser);
router.patch('/user/:userId', UserController.updateUser);


module.exports = router;