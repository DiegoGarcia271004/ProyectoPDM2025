package com.proyectoPdm.seashellinc.premium.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PremiumFeaturesScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "Funciones Premium",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Lista de compuestos ilimitada")
        Text("Balanceador de ecuaciones químicas")
        Text("Sin anuncios")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            navController.navigate("buy_premium")
        }) {
            Text("Actualizar a Premium")
        }
    }
}
