package net.espidev.devievi.storage

object StorageAbstraction {

    fun setStorageType(type: String): Boolean {
        for(typ in StorageType.values()){
            if(typ.name == type){
                setStorageType(typ)
                return true;
            }
        }
        return false
    }
    fun setStorageType(type: StorageType) {

    }

}