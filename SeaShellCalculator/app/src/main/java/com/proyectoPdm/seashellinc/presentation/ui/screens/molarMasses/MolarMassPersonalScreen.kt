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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.presentation.navigation.CompoundScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarMassPersonalScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton.AppButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.AppTextField
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassPersonalViewModel
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
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
    val showDialog by viewModel.showDialog.collectAsState()

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
            IconButton(
                onClick = viewModel::changeShowDialog,
                modifier = Modifier.size(50.dp).shadow(15.dp, RoundedCornerShape(50.dp)),
                colors = IconButtonDefaults.iconButtonColors(CitrineBrown)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Ayuda",
                    tint = Buff,
                    modifier = Modifier.size(30.dp)
                )
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

                        if (filteredList.isEmpty()) {
                            Text(
                                "No hay masas molares agregadas a la lista.",
                                color = Color.Red,
                                modifier = Modifier
                                    .background(Background)
                                    .padding(30.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .background(Background)
                                    .padding(30.dp)
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                            ) {
                                items(filteredList) { item ->
                                    Spacer(Modifier.height(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                navController.navigate(
                                                    CompoundScreenSerializable(item.compound.compoundName)
                                                )
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                item.compound.compoundName,
                                                fontFamily = MontserratFontFamily,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            TextButton(onClick = {/*borrarCompuesto()*/ }) {
                                                Text(
                                                    "Borrar",
                                                    fontFamily = MontserratFontFamily,
                                                    fontWeight = FontWeight.Bold,
                                                    color = DarkBlue
                                                )
                                            }
                                        }
                                    }
                                    Spacer(Modifier.height(10.dp))
                                    HorizontalDivider(color = MainBlue)
                                }
                            }
                        }
                    }
                }
            }
            if (showDialog) {
                AlertDialog(
                    containerColor = Background,
                    onDismissRequest = viewModel::changeShowDialog,
                    confirmButton = {
                        TextButton(onClick = {
                            /*Agregar compuesto()*/ //TODO: Lógica de agregar compuesto
                            viewModel.changeShowDialog()
                        }) {
                            Text(
                                "Agregar",
                                fontFamily = MontserratFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = DarkBlue
                            )
                        }
                    },
                    title = { Text("Agregar nuevo compuesto") },
                    dismissButton = {
                        TextButton(onClick = viewModel::changeShowDialog) {
                            Text(
                                "Cerrar",
                                fontFamily = MontserratFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = DarkBlue
                            )
                        }
                    },
                    text = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            AppTextField(
                                value = "",
                                onValueChange = {},
                                label = "Nombre del compuesto"
                            )
                            Spacer(Modifier.height(15.dp))
                            AppTextField(
                                value = "",
                                onValueChange = {},
                                label = "Fórmula (opcional)"
                            )
                            Spacer(Modifier.height(15.dp))
                            AppTextField(
                                value = "",
                                onValueChange = {},
                                label = "Masa molar"
                            )

                        }
                    }
                )
            }
        }
    }
}