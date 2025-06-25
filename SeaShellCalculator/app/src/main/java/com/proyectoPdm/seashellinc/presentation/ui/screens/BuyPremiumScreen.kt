package com.proyectoPdm.seashellinc.presentation.ui.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.billing.PurchaseStatus
import com.proyectoPdm.seashellinc.presentation.ui.components.LogoComponent
import com.proyectoPdm.seashellinc.presentation.ui.theme.*

import com.proyectoPdm.seashellinc.viewmodel.BillingViewModel
import com.proyectoPdm.seashellinc.viewmodel.PremiumViewModel

@Composable
fun BuyPremiumScreen(
    navController: NavController,
    activity: Activity,
    billingViewModel: BillingViewModel = hiltViewModel(),
    premiumViewModel: PremiumViewModel = hiltViewModel()
) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val status by billingViewModel.purchaseStatus.collectAsState()

    LaunchedEffect(Unit) {
        billingViewModel.initBillingManager()
    }

    LaunchedEffect(status) {
        if (status is PurchaseStatus.Success) {
            premiumViewModel.updatePremiumStatus(true)
            navController.popBackStack()
            billingViewModel.resetStatus()
        }
    }

    val benefitList = listOf(
        "Sin anuncios",
        "Tabla periódica interactiva",
        "Balanceador de ecuaciones",
        "Lista de compuestos ilimitada!"
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF6881AB), DarkBlue),
        startY = 0.1f,
        endY = Float.POSITIVE_INFINITY
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(gradient)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = navigationBarHeight),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoComponent(modifier = Modifier.size(200.dp), 1f)

            Text(
                "SeaShell Premium\n$0.99",
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 36.sp
            )

            Spacer(Modifier.height(75.dp))

            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                benefitList.forEach { item ->
                    Row {
                        Text(
                            "•",
                            modifier = Modifier.padding(end = 8.dp),
                            fontFamily = MontserratFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Text(
                            item,
                            fontFamily = MontserratFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.height(50.dp))
                    }
                }
            }

            Spacer(Modifier.height(70.dp))

            Button(
                onClick = {
                    billingViewModel.launchPurchase(activity, "premium")
                },
                colors = ButtonDefaults.buttonColors(Background),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.border(3.7.dp, Marigold, RoundedCornerShape(5.dp))
            ) {
                Text(
                    "Mejorar a Premium!",
                    color = CitrineBrown,
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(Modifier.height(24.dp))

            when (status) {
                is PurchaseStatus.Loading -> {
                    Text("Procesando compra...", color = Color.White, fontSize = 14.sp)
                }
                is PurchaseStatus.Error -> {
                    Text(
                        (status as PurchaseStatus.Error).message,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
                else -> {}
            }
        }
    }
}
