package com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.massOverMassCalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
// import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyectoPdm.seashellinc.data.local.model.ToCalculate
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.CalcTextField
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.PhysicalCalculatorViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightDarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue


/**
 * Función de la página de calculadora 1
 * Se espera que sea usada como fondo de todas las calculadoras de las unidades físicas de concentración.
 *
 * TODO: Si por algún motivo no está, agregar la dependencia androidx.lifecycle.compose
 * **/
@Preview
@Composable
fun MassOverMassCalculator(viewModel: PhysicalCalculatorViewModel = hiltViewModel()) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    var selectedOutput by remember { mutableStateOf(ToCalculate.CONCENTRATION) }

    val solute by viewModel.solute.collectAsState()
    val solvent by viewModel.solvent.collectAsState()
    val concentration by viewModel.concentration.collectAsState()

    LaunchedEffect(solute, solvent, concentration, selectedOutput) {
        when (selectedOutput) {
            ToCalculate.SOLUTE -> viewModel.calculateRequiredSolute()
            ToCalculate.SOLVENT -> viewModel.calculateRequiredSolvent()
            ToCalculate.CONCENTRATION -> viewModel.calculateConcentrationPercentage()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxHeight()) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(DarkBlue)
                        .padding(paddingValues)
                        .width(18.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(LightDarkBlue)
                        .padding(paddingValues)
                        .width(17.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                Box(Modifier.height(200.dp).background(Color.Black).width(8.dp))
                Spacer(Modifier.width(10.dp))
                Box(Modifier.height(300.dp).background(MainBlue).width(8.dp))
                Spacer(Modifier.width(10.dp))
            }
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = navigationBarHeight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(17.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),

            ){
                Spacer(Modifier.width(50.dp))
                AppGoBackButton(60.dp){}
            }

            Spacer(Modifier.height(16.dp))

            //Title Screen
            Row (
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.7f)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(DarkBlue)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "PORCENTAJE REFERIDO A LA MASA",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = CitrineBrown
                )
            }
            Spacer(Modifier.height(50.dp))
            Text(
                text = "Encontrar:",
                style = MaterialTheme.typography.titleMedium
            )

            //TODO: Fix disposition of this row; also, implement AppButton.kt (hopefully it has changed its onClick issue)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(
                    ToCalculate.CONCENTRATION,
                    ToCalculate.SOLUTE,
                    ToCalculate.SOLVENT
                ).forEach { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = { selectedOutput = option },
                            colors =
                                ButtonDefaults.buttonColors(MainBlue),
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Text(
                                text = when (option) {
                                    ToCalculate.SOLUTE -> "Soluto"
                                    ToCalculate.SOLVENT -> "Solvente"
                                    ToCalculate.CONCENTRATION -> "Concentración"
                                },
                                fontWeight = FontWeight.Bold,
                                color = Buff,
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontSize = 15.sp)
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(70.dp))

            CalcTextField(
                input = solute,
                onValueChange = { viewModel.onSoluteChange(it) },
                label = "Soluto (g)",
                enable = selectedOutput != ToCalculate.SOLUTE
            )
            Spacer(Modifier.height(20.dp))

            CalcTextField(
                input = solvent,
                onValueChange = { viewModel.onSolventChange(it) },
                label = "Solvente (g)",
                enable = selectedOutput != ToCalculate.SOLVENT
            )
            Spacer(Modifier.height(20.dp))

            CalcTextField(
                input = concentration,
                onValueChange = { viewModel.onConcentrationChange(it) },
                label = "Concentración (%)",
                enable = selectedOutput != ToCalculate.CONCENTRATION
            )

            //TODO: Add "clean" button to clear all inputs
        }
    }
}