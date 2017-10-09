package net.espidev.devievi

import java.io.InputStream
import java.util.*

class Track (name: String, id: UUID){
    val name = name
    val id = id
    constructor(name: String, id: UUID, stream: InputStream) : this(name, id){

    }
    //store metadata in cache and fetch audio stream from database
    init {

    }
}