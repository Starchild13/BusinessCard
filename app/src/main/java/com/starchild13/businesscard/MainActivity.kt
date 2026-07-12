package com.starchild13.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.starchild13.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // DEMO ERROR: This line intentionally causes a Task-specific failure.
        // EXPLANATION: This is a syntax error (missing parentheses or invalid statement). 
        // It will cause the ':app:compileDebugKotlin' task to fail during the build process.
        this is not valid kotlin code

        // DEMO SOLUTION:
        // Replace the incorrect configuration above with:
        // // (Remove the invalid line)
        // EXPLANATION: Fixing syntax errors allows the Kotlin compiler to successfully process the source files.
        // Do not apply this solution yet. It is intentionally commented out for the demo.

        setContent {
            BusinessCardTheme {
                BusinessCardScreen()
            }
        }
    }
}
