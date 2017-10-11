package net.espidev.devievi

import jdk.internal.jline.console.ConsoleReader
import jdk.internal.jline.console.CursorBuffer
import net.espidev.devievi.commands.HelpCommand
import net.espidev.devievi.commands.SettingsCommand
import net.espidev.devievi.commands.StopCommand
import net.espidev.devievi.commands.VersionCommand
import net.espidev.devievi.network.SocketServer
import net.espidev.devievi.storage.StorageType
import java.util.*

object DeviEvi {
    val version = "v1.0.0"

    var debug = false
    var tracks = ArrayList<Track>()

    lateinit var socketServer: SocketServer

    /*
     * Track API related functions.
     */

    fun getTrackByUUID(uuid: UUID): UUID? {
        for(track in tracks){
            if(track.id == uuid){
                return track.id
            }
        }
        return null
    }
    fun addTrack() {

    }

    fun stopInstance() {
        System.exit(0);
    }

    /*
     * Utility Functions
     */

    fun println(output: String) {
        stashLine()
        System.out.println(output);
        unstashLine()
    }
    fun debug(output: String) {
        if(debug) {
            println(output)
        }
    }
}

var console: ConsoleReader = ConsoleReader()
private var stashed: CursorBuffer? = null

val commands = ArrayList<ConsoleCommand>()

fun initCommands() {
    commands.add(HelpCommand())
    commands.add(StopCommand())
    commands.add(VersionCommand())
    commands.add(SettingsCommand())
}

fun main(args: Array<String>) {
    System.out.println("Starting DeviEvi v${DeviEvi.version}...")

    System.out.println("Checking preferences...")
    Settings.setup()

    System.out.println("Starting command process...")
    initCommands()
    Thread({ startCommandProcess()}).start()

    println("Starting WebSocket server...")
    Thread({ startSocketServer() }).start()
}

fun startCommandProcess() {
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
        else{
            prompt = false
        }
        Thread.sleep(500)
    }
}

fun processCommand(input: String) {
    val inputParsed = input.split(" ")
    var foundValue = false
    for (cc in commands) {
        if (cc.cName.toLowerCase() == inputParsed[0]) {
            val args = ArrayList<String>()
            var i = 0
            while (i < inputParsed.size) {
                if (i != 0) args.add(inputParsed[i])
                i++
            }
            cc.run(args)
            foundValue = true
            break
        }
    }
    if (!foundValue) println("Do /help for help!")
}

fun startSocketServer() {
    DeviEvi.socketServer = SocketServer(80)
}

fun println(output: String) {
    DeviEvi.println(output);
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