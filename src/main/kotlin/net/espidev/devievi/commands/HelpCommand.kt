package net.espidev.devievi.commands

import net.espidev.devievi.ConsoleCommand
import net.espidev.devievi.DeviEvi
import net.espidev.devievi.commands
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
