package net.espidev.devievi

import java.util.*

object DeviEvi{
    val version = "1.0.0"

    var tracks = ArrayList<Track>()

    fun getTrackByUUID(uuid: UUID): UUID?{
        for(track in tracks){
            if(track.id == uuid){
                return track.id
            }
        }
        return null
    }
    fun addTrack(){

    }
    fun println(output: String){
        System.out.println(output);
    }
}


fun println(output: String){
    DeviEvi.println(output);
}

fun main(args: Array<String>){
    System.out.println("Starting DeviEvi v${DeviEvi.version}...")
}

fun createTrack(){

}