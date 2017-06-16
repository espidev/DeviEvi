var socket = io();
var metadata = [];
var collection = false;
var colcache = 0;

if(getCookie("sessionID") == ""){
    document.cookie = "sessionID=" + guid();
}

socket.on('datastart', function(data) {
    document.getElementById("container").innerHTML = "Refreshing list...";
    meta = new Array(data);
    collection = true;
});
socket.on('cur', function(data){
    colcache = data;
});
socket.on('data', function(data){
   metadata[colcache] = data;
});
socket.on('done', function(data){
    document.getElementById("container").innerHTML = "<div class=\"col s12 m7\">";
    var c = 1;
    console.log(metadata);
    metadata.forEach(function(data){
        var duration = data.duration;
        duration = Math.round(duration/60) + ":" + Math.round(duration%60);
        document.getElementById("container").innerHTML += `
                <div class="waves-effect card horizontal cyan">
                    <div class="card-image">
                    <img src="/images/` + c + `.` + data.picture[0].format + `" height="140" width="140">
                    </div>
                    <div class="card-stacked">
                        <div style="color:white;" class="card-content">
                            <div class="title"><b>` + data.title + `</b></div>
                            <p>` + data.artist.join(", ") + `</p>
                            <p>` + duration + `</p>
                            <p>` + data.album + `</p>
                        </div>
                    </div>
                    <a class="white-text btn btn-floating pulse orange">` + c + `</a>
                </div>
                `;
        c++;
    });
    document.getElementById("container").innerHTML += "</div>"
});

function loginSubmit(){
    var pass = document.getElementById('password').value, user = document.getElementById('username').value;
    socket.emit('attemptLogin', {name: user, 'pass': pass}, function (data){
        if(data == 'nosuchuser'){
            Materialize.toast('No such username!', 4000, 'red');
        }
        if(data == 'passfail'){
            Materialize.toast('Password incorrect!', 4000, 'red');
        }
        if(data == 'success'){

        }
    });
}

/*
 * Helper functions.
 */

function guid() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
        s4() + '-' + s4() + s4() + s4();
}
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}