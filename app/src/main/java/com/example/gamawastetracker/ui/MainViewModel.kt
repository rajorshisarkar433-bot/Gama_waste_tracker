package com.example.gamawastetracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamawastetracker.data.BlackspotReport
import com.example.gamawastetracker.data.TractorLocation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private var database: FirebaseDatabase? = null
    private var tractorRef: DatabaseReference? = null
    private var blackspotsRef: DatabaseReference? = null

    private val _tractors = MutableStateFlow<List<TractorLocation>>(emptyList())
    val tractors: StateFlow<List<TractorLocation>> = _tractors

    private val _blackspots = MutableStateFlow<List<BlackspotReport>>(emptyList())
    val blackspots: StateFlow<List<BlackspotReport>> = _blackspots

    private val _arrivalAlert = MutableStateFlow<String?>(null)
    val arrivalAlert: StateFlow<String?> = _arrivalAlert

    private var simulationJob: Job? = null

    init {
        // Robust initialization to prevent crashes when Firebase is not configured
        runCatching {
            database = FirebaseDatabase.getInstance()
            tractorRef = database?.getReference("tractors")
            blackspotsRef = database?.getReference("blackspots")
            listenToTractors()
            listenToBlackspots()
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun checkDistance(tractor: TractorLocation) {
        val userLat = 12.9716
        val userLon = 77.5946
        val results = FloatArray(1)
        android.location.Location.distanceBetween(userLat, userLon, tractor.latitude, tractor.longitude, results)
        val distKm = results[0] / 1000.0

        if (distKm < 0.5) {
            _arrivalAlert.value = "${tractor.name} is approaching your street! (%.2f km away)".format(distKm)
        }
    }

    private fun listenToTractors() {
        tractorRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tractorList = mutableListOf<TractorLocation>()
                for (child in snapshot.children) {
                    child.getValue(TractorLocation::class.java)?.let { tractorList.add(it) }
                }
                _tractors.value = tractorList
                tractorList.forEach { checkDistance(it) }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun startSimulation() {
        simulationJob?.cancel()
        simulationJob = viewModelScope.launch {
            val userLat = 12.9716
            val userLon = 77.5946

            val currentPositions = MutableList(5) { i ->
                val startLat = userLat + (Random.nextDouble() - 0.5) * 0.05
                val startLon = userLon + (Random.nextDouble() - 0.5) * 0.05

                TractorLocation(
                    id = "tractor_$i",
                    name = "Tractor ${i + 1}",
                    latitude = startLat,
                    longitude = startLon,
                    lastUpdated = System.currentTimeMillis()
                )
            }

            while (true) {
                currentPositions.forEachIndexed { index, tractor ->
                    val dirLat = (userLat - tractor.latitude)
                    val dirLon = (userLon - tractor.longitude)
                    val mag = sqrt(dirLat.pow(2.0) + dirLon.pow(2.0))

                    val stepSize = 0.001
                    val newLat = tractor.latitude + (dirLat / (if (mag == 0.0) 1.0 else mag) * stepSize) + (Random.nextDouble() - 0.5) * 0.0005
                    val newLon = tractor.longitude + (dirLon / (if (mag == 0.0) 1.0 else mag) * stepSize) + (Random.nextDouble() - 0.5) * 0.0005

                    val results = FloatArray(1)
                    android.location.Location.distanceBetween(tractor.latitude, tractor.longitude, newLat, newLon, results)
                    val speedKmph = (results[0] / 1000.0) / (2.0 / 3600.0)

                    val updatedTractor = tractor.copy(
                        latitude = newLat,
                        longitude = newLon,
                        speed = speedKmph,
                        lastUpdated = System.currentTimeMillis()
                    )
                    currentPositions[index] = updatedTractor
                    
                    runCatching {
                        tractorRef?.child(updatedTractor.id)?.setValue(updatedTractor)
                    }
                    checkDistance(updatedTractor)
                }
                _tractors.value = currentPositions.toList()
                delay(2000)
            }
        }
    }

    fun calculateDistanceToUser(tractorLat: Double, tractorLon: Double): Double {
        val userLat = 12.9716
        val userLon = 77.5946
        val results = FloatArray(1)
        android.location.Location.distanceBetween(userLat, userLon, tractorLat, tractorLon, results)
        return (results[0] / 1000.0)
    }

    private fun listenToBlackspots() {
        blackspotsRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reports = mutableListOf<BlackspotReport>()
                try {
                    for (child in snapshot.children) {
                        child.getValue(BlackspotReport::class.java)?.let { report ->
                            reports.add(report)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                _blackspots.value = reports
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun reportBlackspot(report: BlackspotReport) {
        val ref = blackspotsRef ?: return
        val id = ref.push().key ?: return
        ref.child(id).setValue(report.copy(id = id))
    }
}
