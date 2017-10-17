package net.estinet.devievi.commands

import net.estinet.devievi.ConsoleCommand
import net.estinet.devievi.DeviEvi
import java.util.ArrayList

class StopCommand : ConsoleCommand() {
    init {
        super.cName = "stop"
        super.desc = "Stops the java process (not the console). Turns off auto-start."
    }
    override fun run(args: ArrayList<String>){
        DeviEvi.stopInstance()
    }
}

