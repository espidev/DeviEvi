package net.estinet.devievi.network

import net.estinet.devievi.DeviEvi
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

class SocketServer (port: Int) : WebSocketServer() {
    override fun onOpen(con: WebSocket, handshake: ClientHandshake) {

    }
    override fun onClose(con: WebSocket, code: Int, reason: String, remote: Boolean) {

    }
    override fun onMessage(con: WebSocket, message: String) {

    }
    override fun onStart() {
        DeviEvi.println("Server started!")
    }
    override fun onError(conn: WebSocket?, ex: Exception) {
        ex.printStackTrace()
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }
}