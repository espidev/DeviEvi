package net.espidev.devievi.commands

import net.espidev.devievi.ConsoleCommand
import net.espidev.devievi.DeviEvi
import net.espidev.devievi.commands
import java.util.ArrayList

class SettingsCommand : ConsoleCommand() {
    init {
        super.cName = "settings"
        super.desc = "Settings related commands."
    }
    override fun run(args: ArrayList<String>){
        if(args.size == 0) {
            help()
        }
        else if(args.size == 1) {
            if(args[0].toLowerCase() == "help") {
                help()
            }
            else if(args[1].toLowerCase() == "") {

            }
        }
        else {

        }
    }
    private fun help() {
        DeviEvi.println("-----Settings Help-----")
        DeviEvi.println("/settings help | Displays the help menu.")
        DeviEvi.println("/settings list | Lists all of the settings and their values.")
        DeviEvi.println("/settings set <key> <value> | Sets a settings value to a key")
        DeviEvi.println("/settings get <key> | Gets the value for the key")
    }
}