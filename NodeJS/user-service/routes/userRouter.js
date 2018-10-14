const express = require('express');
let router = express.Router();
let UserController = require('../controller/UserController');

router.get('/user', UserController.getAllUser);
router.post('/user', UserController.addUser);
router.delete('/user/:userId',UserController.removeUser);
router.patch('/user/:userId', UserController.updateUser);


module.exports = router;