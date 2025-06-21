package com.proyectoPdm.seashellinc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.proyectoPdm.seashellinc.presentation.navigation.Navigation
import com.proyectoPdm.seashellinc.presentation.ui.screens.ChemicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.PhysicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.theme.SeaShellCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeaShellCalculatorTheme {
                Navigation()
            }
        }
    }
}
