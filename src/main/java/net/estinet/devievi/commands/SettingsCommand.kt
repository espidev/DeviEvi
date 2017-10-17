package net.estinet.devievi.commands

import net.estinet.devievi.ConsoleCommand
import net.estinet.devievi.DeviEvi
import net.estinet.devievi.Settings
import java.util.*

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
            else if(args[0].toLowerCase() == "list") {
                DeviEvi.println("-----Settings List-----")
                for(he in Settings.preflist) {
                    DeviEvi.println("${he.key} : ${Settings.getVal(he.key)}")
                }
            }
            else {
                help()
            }
        }
        else if(args.size == 2) {
            if(args[0].toLowerCase() == "get") {
                val v = Settings.getVal(args[1])
                if(v == null) {
                    DeviEvi.println("Key ${args[1]} not found.")
                }
                else {
                    DeviEvi.println("${args[1]} : ${v}")
                }
            }
            else {
                help()
            }
        }
        else if(args.size == 3) {
            if(args[0].toLowerCase() == "set") {
                val v = Settings.getVal(args[1])
                if(v == null) {
                    DeviEvi.println("Key ${args[1]} not found.")
                }
                else {
                    Settings.setVal(args[1], args[2])
                    DeviEvi.println("Key successfully set")
                }
            }
        }
        else {
            help()
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