const express = require('express');
let router = express.Router();
let logger = require('winston');
let UserController = require('../controller/UserController');

router.get('/user', UserController.getAllUser);
router.post('/user', UserController.addUser);
router.delete('/user/:userId',UserController.removeUser);
module.exports = router;