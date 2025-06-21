package com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightDarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue

/**
 * Función de la página de calculadora 2
 * Se espera que sea usada como fondo de todas las calculadoras de las unidades químicas de concentración.
 *
 * TODO: Para aquel que se encarge de este apartado, deberá de valorar si buscar una forma de reutilizar el fondo, o crear una Screen para cada calculadora
 * **/
@Preview
@Composable
fun CalculatorScreen2() {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = navigationBarHeight)
        ) {
            Row(Modifier.fillMaxSize()) {
                Row(
                    Modifier
                        .width(50.dp)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .height(300.dp)
                            .width(8.dp)
                            .background(MainBlue)
                    )
                    Spacer(Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(8.dp)
                            .background(Color.Black)
                    )
                }
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .background(LightDarkBlue)
                            .fillMaxHeight()
                            .width(17.dp)
                    )
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxHeight()
                            .width(18.dp)
                            .background(DarkBlue)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = navigationBarHeight)
        ) {
            Spacer(Modifier.height(20.dp))
            Row {
                Spacer(Modifier.width(20.dp))
                AppGoBackButton(60.dp){}
            }
        }

    }
}
