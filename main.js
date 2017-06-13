const express = require('express');
const expressSession = require('express-session');
const cookieParser = require('cookie-parser');
const sharedsession = require('express-socket.io-session');
const app = express();
var session = require("express-session")({
   secret: "test-secret",
    userid: "na"
});
var server = require('http').createServer(app);
var io = require('socket.io').listen(server);

var muzik = [];
var sockets = [];
var sessionCookies = [];

/*
 * Init fs.
 */

var fs = require('fs');
if(!fs.existsSync(__dirname + "/data")){
    fs.mkdir(__dirname + "/data");
}
if(!fs.existsSync(__dirname + "/users")){
    fs.mkdir(__dirname + "/users");
}
if(!fs.existsSync(__dirname + "/users/admin.json")){
    fs.writeFileSync(__dirname + "/users/admin.json", '[{"password":"pass123"}]')
}
if(!fs.existsSync(__dirname + "/bind.json")){
    fs.writeFileSync(__dirname + "/bind.json", '[{"' + __dirname.replace("\\", "/") + '/data/EspiDev - Serene.flac":"1"}]');
}
var fs = require('fs');
var json = JSON.parse(fs.readFileSync(__dirname.replace("\\", "/") + "/bind.json", 'utf8'));

/*
 * Scan for user information.
 */

fs.readdir(__dirname + '/users', (err, files) => {
    for(var i = 0; i < files.length; i++){
        var file = files[i];
        console.log(__dirname.replace("\\", "/") + "/data/" + file + " " + json[0][__dirname.replace("\\", "/") + "/data/" + file]);
        console.log(json[0]); //COMMENTS DEBUG
        muzik.push({"position": json[0][__dirname.replace("\\", "/") + "/data/" + file], "file": "data/" + file});
    }
});

/*
* Scan for music files.
*/

fs.readdir(__dirname.replace("\\", "/") + '/data', (err, files) => {
    for(var i = 0; i < files.length; i++){
        var file = files[i];
        console.log(__dirname.replace("\\", "/") + "/data/" + file + " " + json[0][__dirname.replace("\\", "/") + "/data/" + file]);
        console.log(json[0]); //COMMENTS DEBUG
        muzik.push({"position": json[0][__dirname.replace("\\", "/") + "/data/" + file], "file": "data/" + file});
    }
});

/*
 * Create picture cache for music.
 */

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
            console.log(__dirname + "/images/" + i + "." + metadata.picture[0].format);
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
app.use(session);
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
io.use(sharedsession(session));
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

/*
 * Utility functions.
 */

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