package com.proyectoPdm.seashellinc.presentation.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.R
import com.proyectoPdm.seashellinc.billing.BillingClientManager
import com.proyectoPdm.seashellinc.presentation.navigation.MainScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.viewmodel.PremiumViewModel
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    navController: NavController,
    viewModel: PremiumViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as Activity

    var isChecking by remember { mutableStateOf(true) }

    // Verifica el estado de compra actual
    val billingManager = remember {
        BillingClientManager(context) { isPremium ->
            viewModel.updatePremiumStatus(isPremium)
            isChecking = false
        }
    }

    // Lógica de verificación de compras pasadas (útil si reinstalan la app)
    LaunchedEffect(Unit) {
        billingManager.verifyExistingPurchases()
    }

    // Cuando termine de verificar, navega a la pantalla principal
    LaunchedEffect(isChecking) {
        if (!isChecking) {
            delay(1000)
            navController.navigate(MainScreenSerializable)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBlue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.seashelllogo),
            contentDescription = "Logo de SeaShell.Inc",
            modifier = Modifier.size(150.dp)
        )
    }
}
