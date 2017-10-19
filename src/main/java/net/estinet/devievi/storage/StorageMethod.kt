package net.estinet.devievi.storage

import java.util.*

abstract class StorageMethod (val storageType: StorageType) {
    abstract fun setupConfig() //Code that is executed when the configuration is being setup.
    abstract fun startConnection() //starts the connection to database and initializes
    abstract fun getUser(uuid: UUID)

}