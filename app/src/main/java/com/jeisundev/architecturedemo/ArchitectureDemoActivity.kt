package com.jeisundev.architecturedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jeisundev.architecturedemo.core.designsystem.ArchitectureDemoTheme
import com.jeisundev.architecturedemo.presentation.common.navigation.ArchitectureDemoNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArchitectureDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArchitectureDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArchitectureDemoNavHost()
                }
            }
        }
    }
}