package net.espidev.devievi.commands

import net.espidev.devievi.ConsoleCommand
import net.espidev.devievi.DeviEvi
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

