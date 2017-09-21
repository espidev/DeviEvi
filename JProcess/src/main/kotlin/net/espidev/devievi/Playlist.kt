package net.espidev.devievi

import java.util.*

class Playlist constructor(name: String){
    private var list = ArrayList<Track>()
    fun addTrack(track: Track){
        list.add(track);
    }
    fun removeTrack(){
        var tr = Track("hey", UUID.fromString("hi"))

    }
}