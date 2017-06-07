const express = require('express');
const app = express();

app.get('/', function (req, res) {
    res.sendFile(path.join(__dirname + "/index.html"));
});
app.get('/admin', function (req, res) {

});

app.listen(80, function () {
    console.log('Listening on port 80!')
});