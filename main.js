const express = require('express');
const cookieParser = require('cookie-parser');
const app = express();
var server = require('http').createServer(app),
    io = require('socket.io').listen(server),
    cookie = require('cookie');

var muzik = [],
    sockets = [],
    users = [],
    admins = [];

/*
 * Init fs.
 */

console.log("Initializing files...");

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
if(!fs.existsSync(__dirname + "/admin.json")){
    fs.writeFileSync(__dirname + "/admin.json", '[{"name":"admin"}]')
}
admins = JSON.parse(fs.readFileSync(__dirname.replace("\\", "/") + "/admin.json", 'utf8'));
var fs = require('fs');
var json = JSON.parse(fs.readFileSync(__dirname.replace("\\", "/") + "/bind.json", 'utf8'));

/*
 * Scan for user information.
 */

console.log("Indexing user database...");

fs.readdir(__dirname + '/users', (err, files) => {
    for(var i = 0; i < files.length; i++){
        var file = files[i];
        var j = JSON.parse(fs.readFileSync(__dirname + "/users/" + file, 'utf8'));
        console.log({"name": file.split(".")[0], "pass": j[0]['password'], 'sessionID': null});
        users.push({"name": file.split(".")[0], "pass": j[0]['password'], 'sessionID': null});
    }
});

/*
 * Scan for music files.
 */

console.log("Scanning music database...");

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

console.log("Creating cache for album art...");

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

console.log("Starting express.js...");

app.use("/images", express.static(__dirname + '/images'));
app.use("/fonts", express.static(__dirname + '/fonts'));
app.use(express.static('views'));
app.use(cookieParser());
app.get('/', function (req, res) {
    req.session.views = (req.session.views || 0) + 1;
    res.sendFile(path.join(_dirname + "views/index.html"));
});
app.get('/admin.html', function (req, res) {
    if(validateSession(req.cookies.sessionID) != null){
        if(isAdmin(validateSession(req.cookies.sessionID))){
            res.sendFile(path.join(__dirname + "views/admin.html"));
        }
        else{
            res.send("You are not an admin!");
        }
    }
    else{
        res.sendFile(path.join(__dirname + "views/login.html"));
    }
});
app.get('/logout', function (req, res) {
    var b = logoutUser(req.cookies.sessionID);
    if(b){
        res.send("Logged out. <script> window.setTimeout(function(){window.location.replace('index.html');}, 2000); </script>");
    }
    else {
        res.send("You aren't logged in! <script> window.setTimeout(function(){window.location.replace('index.html');}, 2000); </script>");
    }
});

/*
 * Socket.io config
 */


io.on('connection', function(socket){
    var cookief = cookie.parse(socket.handshake.headers.cookie);
    var sessionid = cookief.sessionID;
    console.log(sessionid);
    var user = validateSession(sessionid);

    /*
     * Requested data
     */

    socket.on('getSongList', function(data, callback){
        createList(socket);
    });

    /*
     * Socket.io user stuff
     */

    socket.on('isLoggedIn', function(callback){
        if(user == null){
            callback('no');
        }
        else{
            callback('yes ' + user);
        }
    });
    socket.on('attemptLogin', function(data, callback){
        var name = data.name, pass = data.pass;
        var login = loginUser(name, pass, sessionid);
        callback(login.toLowerCase());
        user = validateSession(sessionid);
    });
    /*
     * Socket.io Playlist Modification
     */
    socket.on('songmod add', function(data, callback){

    });
    console.log('New socket.io connection from ' + socket.id);
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
                socket.emit('pic', metadata.picture[0].data);
                fudge(muzik[++i]);
            });
        }
    }
    fudge(muzik[0]);
}

/*
 * User utility functions.
 */
function validateSession(sessionKey){
    for(var i = 0; i < users.length; i++){
        var user = users[i];
        if(user.sessionID == sessionKey){
            return user.name;
        }
    }
    return null;
}
function loginUser(usern, pass, sessionID){
    for(var i = 0; i < users.length; i++) {
        var user = users[i];
        if (user.name === usern) {
            if (user.pass == pass) {
                user.sessionID = sessionID;
                return "Success";
            }
            else {
                return "PassFail";
            }
        }
    }
    return 'NoSuchUser';
}
function logoutUser(sessionID){
    for(var i = 0; i < users.length; i++){
        var user = users[i];
        if(user.sessionID == sessionID){
            user.sessionID = null;
            return true;
        }
    }
    return false;
}
function isAdmin(usern){
    for(var i = 0; i < users.length; i++){
        var user = users[i];
        if(user.name == usern) return true;
    }
    return false;
}
/*
 * Music Utility functions.
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