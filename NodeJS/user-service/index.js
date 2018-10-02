const express = require('express')
let {serverSettings} = require('./config/config')
const app = express();
// routes go here
app.listen(port, () => {
    console.log(`http://localhost:${serverSettings.port}`)
})