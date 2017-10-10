package net.espidev.devievi.storage

abstract class StorageMethod (storageType: StorageType) {
    val storageType = storageType
    abstract fun setupConfig(); //Code that is executed when the configuration is being setup.
}