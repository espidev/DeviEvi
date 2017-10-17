package net.estinet.devievi.commands

import net.estinet.devievi.ConsoleCommand
import net.estinet.devievi.DeviEvi
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
