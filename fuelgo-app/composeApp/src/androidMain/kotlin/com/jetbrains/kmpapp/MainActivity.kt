package com.jetbrains.kmpapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import android.Manifest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                setContent {
                    App()
                }

            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

        requestPermissionLauncher.launch(Manifest.permission.INTERNET);
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}