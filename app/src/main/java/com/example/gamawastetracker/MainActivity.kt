package com.example.gamawastetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.gamawastetracker.ui.MainScreen
import com.example.gamawastetracker.ui.MainViewModel
import com.example.gamawastetracker.ui.theme.GamaWasteTrackerTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamaWasteTrackerTheme {
                MainScreen(viewModel)
            }
        }
    }
}
