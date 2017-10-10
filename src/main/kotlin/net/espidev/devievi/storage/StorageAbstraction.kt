package net.espidev.devievi.storage

import net.espidev.devievi.DeviEvi
import net.espidev.devievi.Settings

object StorageAbstraction {

    val storageMethods = ArrayList<StorageMethod>()

    fun getStorageType(): StorageType? {
        val type = Settings.getVal("STORAGE_TYPE")
        for(storageType in StorageType.values()) {
            if(storageType.name == type) {
                return storageType
            }
        }
        return null
    }
    fun setStorageType(type: String): Boolean {
        for(typ in StorageType.values()){
            if(typ.name == type){
                setStorageType(typ)
                return true
            }
        }
        return false
    }
    fun setStorageType(type: StorageType) {
        Settings.setVal("STORAGE_TYPE", type.name)
    }
    fun getCurrentStorageMethod(): StorageMethod? {
        for(method in storageMethods) {
            if(method.storageType == getStorageType()){
                return method
            }
        }
        return null
    }

}