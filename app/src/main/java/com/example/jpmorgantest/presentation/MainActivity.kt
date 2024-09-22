package com.example.jpmorgantest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.jpmorgantest.ui.navigation.graph.CustomNavGraph
import com.example.jpmorgantest.ui.theme.JPMorganTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JPMorganTestTheme {
                val startDestination by mainViewModel.startDestination.collectAsState()
                val navController = rememberNavController()

                if (!startDestination.isNullOrBlank()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CustomNavGraph(
                            navController = navController,
                            startDestination = startDestination.orEmpty(),
                            activity = this@MainActivity
                        )
                    }
                }
            }
        }
    }
}
