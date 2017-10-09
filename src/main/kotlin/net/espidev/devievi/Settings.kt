package net.espidev.devievi

import java.util.prefs.Preferences

object Settings {
    lateinit var prefs: Preferences
    fun setup() {
        prefs = Preferences.userNodeForPackage(this.javaClass)
    }
}