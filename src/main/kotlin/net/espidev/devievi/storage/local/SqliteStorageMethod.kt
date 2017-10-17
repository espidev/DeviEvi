package net.espidev.devievi.storage.local

import net.espidev.devievi.storage.StorageMethod
import net.espidev.devievi.storage.StorageType

class SqliteStorageMethod: StorageMethod(storageType = StorageType.SQLITE) {
    override fun setupConfig() {

    }

}