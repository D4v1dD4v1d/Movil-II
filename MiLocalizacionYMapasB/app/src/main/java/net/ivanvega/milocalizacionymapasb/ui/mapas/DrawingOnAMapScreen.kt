package net.ivanvega.milocalizacionymapasb.ui.mapas

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState


@Composable
fun DrawingMap(){
    GoogleMap(
        googleMapOptionsFactory = {
            GoogleMapOptions().mapId("DEMO_MAP_ID")
        },
        //...
    ) {
        AdvancedMarker(
            state = MarkerState(position = LatLng(47.60535637906227, -122.33363217834844)),
            title = "Marker in Seattle"
        )
        AdvancedMarker(
            state = MarkerState(position = LatLng(65.05515026454427, -19.616424875742076)),
            title = "Marker in Iceland"
        )
    }
}