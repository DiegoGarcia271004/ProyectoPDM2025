package com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.presentation.navigation.CompoundScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppTextField
import com.proyectoPdm.seashellinc.presentation.ui.theme.*
import com.proyectoPdm.seashellinc.premium.util.PremiumManager
import com.proyectoPdm.seashellinc.premium.iu.components.PremiumUpgradeDialog

@Composable
fun MolarMassPersonalScreen(
    navController: NavController,
    viewModel: MolarMassPersonalViewModel = hiltViewModel()
) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val query by viewModel.query.collectAsState()
    val filteredList by viewModel.filteredList.collectAsState()

    val isPremium = PremiumManager.isPremium()
    var showPremiumDialog by remember { mutableStateOf(false) }

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

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(30.dp))

                AppTextField(
                    value = query,
                    onValueChange = viewModel::onValueChange,
                    label = "Buscar Compuesto"
                )

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MainBlue)
                        .padding(20.dp)
                        .height(500.dp)
                        .fillMaxWidth(0.85f)
                ) {
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Background)
                                    .padding(30.dp)
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(color = MainBlue)
                            }
                        }
                    } else if (errorMessage.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .background(Background)
                                .padding(30.dp)
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            Text(
                                errorMessage,
                                fontFamily = MontserratFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = CitrineBrown
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .background(Background)
                                .padding(30.dp)
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            items(filteredList) { item ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate(
                                                CompoundScreenSerializable(item.compoundName)
                                            )
                                        }
                                ) {
                                    Text(
                                        item.compoundName,
                                        fontFamily = MontserratFontFamily,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Botón para agregar compuesto
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(CitrineBrown)
                        .clickable {
                            if (!isPremium && filteredList.size >= 5) {
                                showPremiumDialog = true
                            } else {
                                viewModel.agregarNuevoCompuesto()
                            }
                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        "Agregar nuevo compuesto",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Diálogo si intenta agregar más sin ser premium
        if (showPremiumDialog) {
            PremiumUpgradeDialog(
                onDismiss = { showPremiumDialog = false },
                onUpgrade = {
                    showPremiumDialog = false
                    navController.navigate("buy_premium")
                }
            )
        }
    }
}
