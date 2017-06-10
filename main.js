const express = require('express');
const app = express();

app.use(express.static('views'));
app.use(function(req, res, next){
    console.log('[ERROR] Client error 404');
    res.sendFile(__dirname + "/views/404.html");
});
app.get('/', function (req, res) {
    res.sendFile(path.join(_dirname + "views/index.html"));
});
app.get('/admin', function (req, res) {

});

app.listen(80, function () {
    console.log('Listening on port 80!')
});

function createList(){

}
function addMusic(){

}
function removeMusic(){

}
function playMusic(){

}
function pauseMusic(){

}
function stopMusic(){

}