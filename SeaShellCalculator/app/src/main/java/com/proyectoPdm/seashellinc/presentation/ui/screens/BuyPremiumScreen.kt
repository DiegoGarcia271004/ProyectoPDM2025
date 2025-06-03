package com.proyectoPdm.seashellinc.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton
import com.proyectoPdm.seashellinc.presentation.ui.components.LogoComponent
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.Marigold
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily


@Composable
fun BuyPremiumScreen() {
    val navigationBarHeigh = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val benefitList = listOf(
        "Sin anuncios",
        "Tabla periódica interactiva",
        "Balanceador de ecuaciones",
        "Lista de compuestos ilimitada!"
    )
    val gradientColors = listOf(
        Color(0xFF6881AB),
        DarkBlue
    )
    val gradient = Brush.verticalGradient(
        colors = gradientColors,
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
            modifier = Modifier.fillMaxSize().padding(bottom = navigationBarHeigh),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
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
                modifier = Modifier
                    .padding(start = 16.dp),
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
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )
                        Text(
                            item, fontFamily = MontserratFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.height(50.dp))
                    }
                }
            }
            Spacer(Modifier.height(70.dp))
            Button(
                onClick = {},
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
        }
    }
}