package com.example.gamawastetracker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(viewModel: MainViewModel) {
    val tractors by viewModel.tractors.collectAsState()
    val blackspots by viewModel.blackspots.collectAsState()
    val alert by viewModel.arrivalAlert.collectAsState()

    val initialPosition = LatLng(12.9716, 77.5946)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 14f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            // User Location Marker
            Marker(
                state = rememberMarkerState(position = initialPosition),
                title = "My Location",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
            )

            tractors.forEach { tractor ->
                val distance = viewModel.calculateDistanceToUser(tractor.latitude, tractor.longitude)
                val tractorLatLng = LatLng(tractor.latitude, tractor.longitude)

                Marker(
                    state = rememberMarkerState(key = tractor.id, position = tractorLatLng),
                    title = tractor.name,
                    snippet = "Speed: %.1f km/h | Dist: %.2f km".format(tractor.speed, distance),
                )
            }

            blackspots.forEach { spot ->
                Marker(
                    state = rememberMarkerState(key = spot.id, position = LatLng(spot.latitude, spot.longitude)),
                    title = "Blackspot",
                    snippet = spot.description,
                )
            }
        }

        // Simulation Button
        Button(
            onClick = { viewModel.startSimulation() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
        ) {
            Text("Start Advanced Simulation")
        }

        alert?.let {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            ) {
                Text(
                    text = it,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}
