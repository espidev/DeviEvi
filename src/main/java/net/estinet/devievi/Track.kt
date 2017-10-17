package net.estinet.devievi

import java.io.InputStream
import java.util.*

class Track (val name: String, val id: UUID){
    constructor(name: String, id: UUID, stream: InputStream) : this(name, id){

    }
    //store metadata in cache and fetch audio stream from database
    init {

    }
}