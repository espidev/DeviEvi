var server = require('http').createServer(app),
    io = require('socket.io').listen(server),
    cookie = require('cookie');