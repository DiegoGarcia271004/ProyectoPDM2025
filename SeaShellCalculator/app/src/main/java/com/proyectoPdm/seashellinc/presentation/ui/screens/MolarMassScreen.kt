package com.proyectoPdm.seashellinc.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.premium.iu.components.PremiumUpgradeDialog
import com.proyectoPdm.seashellinc.viewmodel.PremiumViewModel
import androidx.compose.runtime.livedata.observeAsState

data class Compound(val name: String, val molarMass: Double)

@Composable
fun MolarMassPersonalScreen(
    navController: NavController,
    viewModel: PremiumViewModel = viewModel()
) {
    val isPremium by viewModel.isPremium.observeAsState(initial = false)
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    var showDialog by remember { mutableStateOf(false) }

    // Simulación local, CAMBIAR
    val compoundList = remember { mutableStateListOf<Compound>() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(bottom = navigationBarHeight)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End,
            ) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(8.dp)
                        .background(Color.Black)
                )
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .background(MainBlue)
                        .height(8.dp)
                )
                Spacer(Modifier.height(30.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (isPremium || compoundList.size < 5) {
                    compoundList.add(Compound("H₂O", 18.015))
                } else {
                    showDialog = true
                }
            }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(20.dp))
            Row {
                Spacer(Modifier.width(20.dp))
                AppGoBackButton(60.dp) {
                    navController.popBackStack()
                }
            }

            Spacer(Modifier.height(16.dp))

            // Lista de compuestos
            if (compoundList.isEmpty()) {
                Text(
                    "No hay compuestos guardados.",
                    modifier = Modifier.padding(20.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(compoundList) { compound ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(compound.name)
                                Text("${compound.molarMass} g/mol")
                            }
                        }
                    }
                }
            }

            // Diálogo de actualización a Premium
            if (showDialog) {
                PremiumUpgradeDialog(
                    onUpgrade = {
                        showDialog = false
                        navController.navigate("buy_premium")
                    },
                    onDismiss = {
                        showDialog = false
                    }
                )
            }
        }
    }
}
