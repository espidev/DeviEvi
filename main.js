const express = require('express');
const app = express();
var server = require('http').createServer(app);
var io = require('socket.io').listen(server);

var muzik = [];
var sockets = [];

var fs = require('fs');
if(!fs.existsSync(__dirname + "/data")){
    fs.mkdir(__dirname + "/data");
}
if(!fs.existsSync(__dirname + "/bind.json")){
    fs.writeFileSync(__dirname + "/bind.json", '[{"' + __dirname.repla + '/data/EspiDev - Serene.flac":"1"}]');
}
var fs = require('fs');
var json = JSON.parse(fs.readFileSync(__dirname + "/bind.json", 'utf8'));
fs.readdir(__dirname + '/data', (err, files) => {
    for(var i = 0; i < files.length; i++){
        var file = files[i];
        console.log(__dirname + "/data/" + file + " " + json[__dirname + "/" + file]);
        console.log(json[0]);
        muzik.push({"position": json[0][__dirname + "/" + file], "file": "data/" + file});
    }
});

for(var i = 0; i < muzik.length; i++){
    var mm = require('musicmetadata');
    var parser = mm(fs.createReadStream(muzik[i].file), function (err, metadata) {
        if (err) throw err;
        fs.writeFileSync(__dirname + "/images/" + i + "." + metadata.picture[0].format, new Buffer(metadata.picture[0].data));
        var easyimg = require('easyimage');
        var height = 0, width = 0;
        easyimg.info(__dirname + "/images/" + i + "." + metadata.picture[0].format).then(function (file){
            height = file.height;
            width = file.width;
            console.log(__dirname + "/images/" + i + "." + metadata.picture[0].format)
            easyimg.crop({
                src:__dirname + "/images/" + i + "." + metadata.picture[0].format,
                dst:__dirname + "/images/" + i + "." + metadata.picture[0].format,
                cropwidth:height,
                x:0, y:0
            });
        });
    });
}
app.use("/images", express.static(__dirname + '/images'));
app.use(express.static('views'));
/*app.use(function(req, res, next){
 console.log('[ERROR] Client error 404');
 res.sendFile(__dirname + "/views/404.html");
 });*/
app.get('/', function (req, res) {
    res.sendFile(path.join(_dirname + "views/index.html"));
});
app.get('/admin', function (req, res) {
    res.sendFile(path.join(_dirname + "views/admin.html"));
});
io.on('connection', function(socket){
    console.log('[INFO] New Socket.io client connection.');
    createList(socket);
    sockets[socket.id] = socket;
});
io.on('disconnect', function(socket){
    delete sockets[socket.id];
});

server.listen(80, function () {
    console.log('Listening on port 80!')
});

function createList(socket){
    var i = 0;
    socket.emit('datastart');
    function fudge(data){
        if(i == muzik.length-1){
            var mm = require('musicmetadata');
            var parser = mm(fs.createReadStream(data.file), function (err, metadata) {
                if (err) throw err;
                socket.emit('cur', data.position);
                socket.emit('data', metadata);
                socket.emit('pic', metadata.picture[0].data);
                socket.emit('done');
            });
        }
        else{
            console.log(data);
            var mm = require('musicmetadata');
            var parser = mm(fs.createReadStream(data.file), function (err, metadata) {
                if (err) throw err;
                socket.emit('cur', data.position);
                socket.emit('data', metadata);
                socket.emit('pic', metadata.picture.data);
                fudge(muzik[++i]);
            });
        }
    }
    fudge(muzik[0]);
}
function addMusic(){

}
function removeMusic(){

}
function moveSong(prev, cur){

}
function playMusic(){

}
function pauseMusic(){

}
function stopMusic(){

}