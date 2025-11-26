

package com.starchild13.businesscard


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize context for shared UI
        appContextForShared = applicationContext

        setContent {
            // Call the shared UI from commonMain
            Scaffold() {  BusinessCardScreen() }

        }
    }
}

