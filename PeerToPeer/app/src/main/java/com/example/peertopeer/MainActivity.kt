package com.example.peertopeer

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.peertopeer.ui.theme.PeerToPeerTheme

class MainActivity : ComponentActivity() {

    // Variables para manejar WifiP2p
    private lateinit var manager: WifiP2pManager
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var receiver: BroadcastReceiver
    private lateinit var intentFilter: IntentFilter

    // Lista mutable para almacenar los dispositivos encontrados
    private val devices = mutableStateListOf<WifiP2pDevice>()

    // Launcher para solicitar permisos
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                discoverPeers()  // Si el permiso es concedido, se inician la búsqueda de peers
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialización de WifiP2pManager y el canal
        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, mainLooper, null)

        // Configuración de los filtros de intención para detectar cambios en WiFi Direct
        intentFilter = IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }

        // Inicialización del receptor de broadcast para manejar los eventos de WiFi Direct
        receiver = WiFiDirectBroadcastReceiver(manager, channel, this) { peerList: List<WifiP2pDevice> ->
            devices.clear()
            devices.addAll(peerList)
        }

        // Configuración de la interfaz de usuario usando Jetpack Compose
        setContent {
            PeerToPeerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        devices = devices,
                        onSearchClicked = { checkPermissionAndDiscover() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Registrar el receptor de broadcast cuando la actividad está en primer plano
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        // Anular el registro del receptor de broadcast cuando la actividad no está en primer plano
        unregisterReceiver(receiver)
    }

    private fun checkPermissionAndDiscover() {
        // Comprobar si el permiso de ubicación está concedido
        when {
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED -> {
                discoverPeers()
            }
            else -> {
                // Solicitar permiso de ubicación si no está concedido
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun discoverPeers() {
        // Iniciar la búsqueda de peers usando WiFi Direct
        manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Toast.makeText(this@MainActivity, "Discovery Initiated", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(reasonCode: Int) {
                Toast.makeText(this@MainActivity, "Discovery Failed: $reasonCode", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

@Composable
fun MainScreen(
    devices: List<WifiP2pDevice>,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Definición de la pantalla principal de la aplicación
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para iniciar la búsqueda de dispositivos
        Button(onClick = onSearchClicked) {
            Text(text = "Buscar Dispositivos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de dispositivos encontrados
        DeviceList(devices = devices)
    }
}

@Composable
fun DeviceList(devices: List<WifiP2pDevice>, modifier: Modifier = Modifier) {
    // Definición de la lista de dispositivos en la interfaz de usuario
    Column(modifier = modifier) {
        for (device in devices) {
            Text(text = device.deviceName)
        }
    }
}
