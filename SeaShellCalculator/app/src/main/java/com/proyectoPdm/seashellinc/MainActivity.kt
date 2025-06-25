package com.proyectoPdm.seashellinc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.ads.MobileAds
import com.proyectoPdm.seashellinc.presentation.navigation.Navigation
import com.proyectoPdm.seashellinc.presentation.ui.theme.SeaShellCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        MobileAds.initialize(this){}

        setContent {
            SeaShellCalculatorTheme {
                Navigation()
            }
        }
    }
}

