package com.example.gamawastetracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gamawastetracker.R
import com.example.gamawastetracker.data.BlackspotReport
import kotlinx.coroutines.launch

@Composable
fun ReportScreen(viewModel: MainViewModel) {
    var description by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(R.string.report_blackspot), style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { /* Simulate taking photo */ }) {
                Text(stringResource(R.string.take_photo))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (description.isNotBlank()) {
                        viewModel.reportBlackspot(
                            BlackspotReport(
                                latitude = 12.9716,
                                longitude = 77.5946,
                                description = description,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        description = ""
                        scope.launch {
                            snackbarHostState.showSnackbar("Blackspot reported successfully!")
                        }
                    }
                }
            ) {
                Text(stringResource(R.string.upload))
            }
        }
    }
}
