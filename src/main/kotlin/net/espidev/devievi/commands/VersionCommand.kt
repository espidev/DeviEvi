package net.espidev.devievi.commands

import net.espidev.devievi.ConsoleCommand
import net.espidev.devievi.DeviEvi
import java.util.ArrayList

class VersionCommand : ConsoleCommand(){
    init {
        super.cName = "version"
        super.desc = "Displays the version number for this instance of EstiConsole."
    }
    override fun run(args: ArrayList<String>){
        DeviEvi.println(DeviEvi.version)
    }
}
