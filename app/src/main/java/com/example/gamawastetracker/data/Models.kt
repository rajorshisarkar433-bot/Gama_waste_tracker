package com.example.gamawastetracker.data

data class TractorLocation(
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val speed: Double = 0.0,
    val lastUpdated: Long = 0,
    val name: String = "Kachara Gaadi"
)

data class BlackspotReport(
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val photoUrl: String = "",
    val timestamp: Long = 0,
    val description: String = ""
)
