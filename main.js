const express = require('express');
const app = express();
var server = require('http').createServer(app);
var io = require('socket.io')(server);

var muzik = [{"position":"1", "file":__dirname + "data/EspiDev - Serene.flac"}];
var sockets = [];

var fs = require('fs');
if(!fs.existsSync(__dirname + "/data")){
    fs.mkdir(__dirname + "/data");
}

app.use(express.static('views'));
app.use(function(req, res, next){
    console.log('[ERROR] Client error 404');
    res.sendFile(__dirname + "/views/404.html");
});
app.get('/', function (req, res) {
    res.sendFile(path.join(_dirname + "views/index.html"));
});
app.get('/admin', function (req, res) {
    res.sendFile(path.join(_dirname + "views/admin.html"));
});

io.on('connection', function(socket){
    console.log('[INFO] New Socket.io client connection.');
    socket.emit('data', createList());
    sockets[socket.id] = socket;
});
io.on('disconnect', function(socket){
    delete sockets[socket.id];
});

app.listen(80, function () {
    console.log('Listening on port 80!')
});

function createList(){
    var str = "";
    muzik.forEach(function(data){
        var mm = require('musicmetadata');
        var parser = mm(fs.createReadStream(data.file), function (err, metadata) {
            if (err) throw err;
            str += data.position + ":" + metadata.artist + ":" + metadata.album + ":" + metadata.albumartist + ":" + metadata.title + ":" + metadata.genre + ":" + metadata.picture + ":" + metadata.duration + " ";
        });
    });
    return str;
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