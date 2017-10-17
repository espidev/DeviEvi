package net.estinet.devievi

import net.estinet.devievi.storage.StorageAbstraction
import java.util.prefs.Preferences

object Settings {
    lateinit var prefs: Preferences

    val setupPrefs = ArrayList<Runnable>()
    val preflist = HashMap<String, String>()

    private val mthr = Thread({ firstSetup() })

    private var lock = false

    init {

        /*
         * General settings
         */

        preflist.put("PORT", "1305")
        preflist.put("STORAGE_TYPE", "SQLITE")
        preflist.put("FIRST_SETUP", "false")

        setupPrefs.add(Runnable({
            while (true) { //PORT settings validataion
                println("Port that server runs on (Default: ${preflist["PORT"]}): ")
                var v = console.readLine()
                try {
                    if(v == "") v = preflist["PORT"]
                    if(Integer.parseInt(v) > 65535 || Integer.parseInt(v) < 0) {
                        throw Exception()
                    }
                    preflist.put("PORT", v)
                    setVal("PORT", v)
                    break
                }
                catch(e: Exception) {
                    println("Incorrect port number. Please try again.")
                }
            }
            while (true) { //STORAGE_TYPE settings validation
                println("Storage type the server should use (Default: ${preflist["STORAGE_TYPE"]}): ")
                var v: String? = console.readLine().toUpperCase();
                if(v == "") v = preflist["STORAGE_TYPE"]
                preflist.put("STORAGE_TYPE", v!!)
                var b = false
                for(c in StorageAbstraction.storageMethods) {
                    if(c.storageType.name == preflist["STORAGE_TYPE"]) {
                        StorageAbstraction.setStorageType(c.storageType)
                        b = !b
                        break
                    }
                }
                if(b) {
                    setVal("STORAGE_TYPE", preflist["STORAGE_TYPE"]!!)
                    StorageAbstraction.addCurrentStorageMethodToConfig()
                    break
                }
                println("Incorrect storage type! Try again.")
            }

        }))
        setupPrefs.add(Runnable({
            StorageAbstraction.getCurrentStorageMethod()!!.setupConfig()
        }))
    }

    fun setup() {
        prefs = Preferences.userNodeForPackage(this.javaClass)
        for(key in preflist.keys) {
            if(!prefs.nodeExists(key)) {
                prefs.put(key, preflist[key])
            }
            else {
                preflist[key] = prefs.get(key, preflist[key])
            }
        }
        if(getVal("FIRST_SETUP") != "true") {
            println("Setting up for the first time, you can continue the setup process here, or use a client to access and setup.")
            mthr.start()
            val thr = Thread({while(getVal("FIRST_SETUP") != "true") {
                    Thread.sleep(2000)
                }
                try{
                    mthr.interrupt()
                }
                catch (e: Exception) {}
                lock = false
                println("Completed initial setup!")
            })
            lock = true
            while(lock) Thread.sleep(1000) //locks the main thread until the first setup is complete
        }
    }
    private fun firstSetup() {
        for(pref in setupPrefs) pref.run()
        setVal("FIRST_SETUP", "true")
    }
    fun getVal(key: String): String {
        return prefs.get(key, null)
    }
    fun setVal(key: String, value: String) {
        preflist[key] = value
        prefs.put(key, value)
    }
}