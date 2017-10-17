package net.estinet.devievi.commands

import net.estinet.devievi.ConsoleCommand
import net.estinet.devievi.DeviEvi
import net.estinet.devievi.commands
import java.util.*

class HelpCommand : ConsoleCommand() {
    init {
        super.cName = "help"
        super.desc = "Displays help for commands."
    }
    override fun run(args: ArrayList<String>){
        DeviEvi.println("----------Help----------")
        for(command in commands) DeviEvi.println("${command.cName} : ${command.desc}")
    }
}
