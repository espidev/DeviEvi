var socket = io();
var metadata = [];
var collection = false;
var colcache = 0;

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