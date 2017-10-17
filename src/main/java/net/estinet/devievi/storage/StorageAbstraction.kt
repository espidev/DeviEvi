package net.estinet.devievi.storage

import net.estinet.devievi.Settings
import net.estinet.devievi.storage.local.SqliteStorageMethod
import java.util.*

object StorageAbstraction {

    val storageMethods = ArrayList<StorageMethod>()
    private var storageTypeCache: StorageType? = null

    init {
        storageMethods.add(SqliteStorageMethod())
    }

    fun getStorageTypeCache(): StorageType? {
        if(storageTypeCache == null) {
            storageTypeCache = getStorageType()
            return storageTypeCache
        }
        else {
            return storageTypeCache
        }
    }
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
        storageTypeCache = type
    }
    fun getCurrentStorageMethod(): StorageMethod? {
        for(method in storageMethods) {
            if(method.storageType == getStorageTypeCache()){
                return method
            }
        }
        return null
    }

    fun initializeStorageMethod() {
        val s = getCurrentStorageMethod()
        if(s == null){
            println("Error, storage method not initialized!")
            throw Exception()
        }
        else {
            s.startConnection()
        }
    }


    fun obtainTrackStream(uuid: UUID) {

    }
}