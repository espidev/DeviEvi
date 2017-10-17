package net.estinet.devievi.accounts

import java.util.*

object Accounts {
    val userBase = HashMap<UUID, User>()

    fun getUserFromUUID(uuid: UUID): User? {
        return userBase[uuid]
    }
    fun userHasPerm(uuid: UUID, perm: String): Boolean? {
        try{
            return getUserFromUUID(uuid)!!.role.permissions.contains(perm)
        }
        catch (e: Exception) {
            return null
        }
    }
}