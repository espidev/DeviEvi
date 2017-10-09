package net.espidev.devievi

import java.util.*

class Playlist constructor(name: String, type: PlaylistType){
    private var list = ArrayList<UUID>()
    fun addTrack(track: UUID){
        list.add(track)
    }
    fun removeTrack(track: UUID){
        list.remove(track)
    }
    /*
     * Returns a list of tracks for the playlist by id.
     * @return ArrayList<UUID> of tracks by id.
     */
    fun getTracks(): ArrayList<UUID>{
        return list
    }
    fun hasTrack(track: UUID): Boolean{
        return list.contains(track)
    }
}