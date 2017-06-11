var socket = io();
socket.on('data', function(data) {
    load(data);
});

function load(data){
    console.log(data);
    document.getElementById("container").innerHTML = "<div class=\"col s12 m7\">";
    var sp = data.split("§");
    for(var i = 1; i <= sp.length;){
        for(var j = 0; j <= sp.length; j++){
            var st = sp[j].split("Հ");
            if(st[0] == i){
                i++;
                document.getElementById("container").innerHTML += `
                <div class="waves-effect card horizontal cyan">
                    <div class="card-image">
                    <img height="140" width="140" src="` + st[1].picture + `">
                    </div>
                    <div class="card-stacked">
                        <div style="color:white;" class="card-content">
                            <div class="title"><b>` + st[1].title + `</b></div>
                            <p>` + st[1].artist.join(", ") + `</p>
                            <p>` + st[1].duration + `</p>
                            <p>` + st[1].album + `</p>
                        </div>
                    </div>
                    <a class="white-text btn btn-floating pulse orange">1</a>
                </div>
                `;
            }
        }
    }
    document.getElementById("container").innerHTML += "</div>"
}