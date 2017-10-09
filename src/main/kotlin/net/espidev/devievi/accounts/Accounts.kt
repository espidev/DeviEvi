package net.espidev.devievi.accounts

import java.util.*

object Accounts {
    val userBase = HashMap<UUID, User>()

    fun getUserFromUUID(uuid: UUID): User? {
        return userBase[uuid]
    }
}