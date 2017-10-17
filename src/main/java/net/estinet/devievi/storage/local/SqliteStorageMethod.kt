package net.estinet.devievi.storage.local

import net.estinet.devievi.DeviEvi
import net.estinet.devievi.console
import net.estinet.devievi.storage.StorageMethod
import net.estinet.devievi.storage.StorageType
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*


class SqliteStorageMethod: StorageMethod(storageType = StorageType.SQLITE) {
    var url = "jdbc:sqlite:" + System.getProperty(".") + "/DevieviData.db"

    /*
     * Currently connections are held and not reopened. Can be subject to change.
     */

    var conn: Connection? = null

    override fun startConnection() {
        if(!File(url.substring(12)).exists()) {
            try {
                val conn: Connection = DriverManager.getConnection(url)
                val meta = conn.metaData
                println("The driver name is " + meta.driverName)
                println("A new database has been created.")
                conn.close()
            } catch (e: SQLException) {
                println(e.message!!)
            }
        }
        try {
            println("Attempting SQLite connection with $url...")
            conn = DriverManager.getConnection(url)
            println("Successful SQLite connection.")

            /*
             * Initialization of tables.
             */

            val initUsers = "CREATE TABLE IF NOT EXISTS users (uuid text PRIMARY KEY, username text, password text, rank text)"
            val initRoles = "CREATE TABLE IF NOT EXISTS roles (name text, perms text, users text)"
            val initTracks = "CREATE TABLE IF NOT EXISTS tracks (uuid text, track blob)"
            val initPlaylists = "CREATE TABLE IF NOT EXISTS playlists (uuid text, tracks text)"
            val initSettings = "CREATE TABLE IF NOT EXISTS settings (key text, value text)"

            try {
                val st = conn!!.createStatement()
                st.execute(initUsers)
                st.execute(initRoles)
                st.execute(initTracks)
                st.execute(initPlaylists)
                st.execute(initSettings)
            } catch (e: SQLException) {
                println(e.message)
            }

        }
        catch(ex: SQLException) {
            println(ex.message!!)
        }
        finally {
            try {
                if(conn != null) conn!!.close()
            }
            catch(ex: SQLException) {
                println(ex.message!!)
            }
        }
    }

    override fun setupConfig() {
        while(true) {
            DeviEvi.println("Where would you like to store the sqlite file? (" + System.getProperty(".") + ")")
            var v = console.readLine()
            if(v == "") v = System.getProperty(".")
            //add any extra validation here if needed
            url = "jdbc:sqlite:$v/DeviEviData.db"
            break
        }
    }

    override fun getUser(uuid: UUID) {

    }

    fun println(input: String) {
        DeviEvi.println("[SQLite] $input")
    }
}