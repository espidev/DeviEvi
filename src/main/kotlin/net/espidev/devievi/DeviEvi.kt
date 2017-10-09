package net.espidev.devievi

import java.util.*

object DeviEvi{
    val version = "1.0.0"

    var debug = false
    var tracks = ArrayList<Track>()

    /*
     * Track API related functions.
     */

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

    /*
     * Utility Functions
     */

    fun println(output: String){
        System.out.println(output);
    }
    fun debug(output: String) {
        if(debug) {
            println(output)
        }
    }
}


fun println(output: String){
    DeviEvi.println(output);
}

fun main(args: Array<String>){
    System.out.println("Starting DeviEvi v${DeviEvi.version}...")
    System.out.println("Starting command process...")
    Thread({ startCommandProcess()}).start();
}

fun startCommandProcess(){
    var prompt = true
    while (true) {
        if(prompt){
            console.setPrompt(">");
        }
        val input = console.readLine()
        if(input != null && !input.trim().equals("")){
            if(DeviEvi.debug){
                println("Inputted command: $input")
            }
            processCommand(input)
            prompt = true
        }
        else{agsdgsad
            prompt = false
        }
        Thread.sleep(500)
    }
}

fun stashLine() {
    stashed = console.getCursorBuffer().copy();
    try {
        console.getOutput().write("\u001b[1G\u001b[K");
        console.flush()
    } catch (e: Exception) {
        // ignore
    }
}

fun unstashLine() {
    try {
        console.resetPromptLine(console.getPrompt(), stashed.toString(), stashed!!.cursor)
    } catch (e: Exception) {
        // ignore
    }
}