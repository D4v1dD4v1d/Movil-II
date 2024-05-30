package com.example.peertopeer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast

// Receptor de broadcast personalizado para manejar eventos relacionados con Wi-Fi Direct
class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: MainActivity,
    private val onPeersAvailable: (List<WifiP2pDevice>) -> Unit
) : BroadcastReceiver() {

    // Método que se llama cuando se recibe un broadcast
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action  // Obtener la acción del intent recibido

        when (action) {
            // Acción cuando cambia el estado del Wi-Fi P2P
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    // Wi-Fi P2P está habilitado
                    Toast.makeText(context, "Wi-Fi P2P is enabled", Toast.LENGTH_SHORT).show()
                } else {
                    // Wi-Fi P2P no está habilitado
                    Toast.makeText(context, "Wi-Fi P2P is not enabled", Toast.LENGTH_SHORT).show()
                }
            }
            // Acción cuando cambia la lista de peers disponibles
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                // Solicitar la lista de peers al manager
                manager.requestPeers(channel) { peers ->
                    // Pasar la lista de peers al callback proporcionado
                    onPeersAvailable(peers.deviceList.toList())
                }
            }
            // Acción cuando cambia la conexión Wi-Fi P2P
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                // Manejar nuevas conexiones o desconexiones
            }
            // Acción cuando cambia el estado de este dispositivo
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                // Manejar cambios en el estado Wi-Fi de este dispositivo
            }
        }
    }
}
