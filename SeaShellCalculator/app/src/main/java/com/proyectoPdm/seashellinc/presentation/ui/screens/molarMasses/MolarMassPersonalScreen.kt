package com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.proyectoPdm.seashellinc.presentation.navigation.MolarMassPersonalScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppTextField
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassPersonalViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily


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

            Column(modifier = Modifier.padding(16.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppTextField(
                        value = query,
                        onValueChange = viewModel::onValueChange,
                        label = "Buscar Compuesto"
                    )
                }

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
                    } else if (!errorMessage.isEmpty()) {
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
            }
        }

    }
}