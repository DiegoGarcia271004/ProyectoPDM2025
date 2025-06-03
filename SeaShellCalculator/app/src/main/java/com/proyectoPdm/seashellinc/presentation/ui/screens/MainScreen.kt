package com.proyectoPdm.seashellinc.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.R
import com.proyectoPdm.seashellinc.presentation.navigation.BalEquationScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.ChemicalUnitsScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.LoginScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.MolarMassScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.PeriodicTableScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.PhysicalUnitsScreenSerializable
import com.proyectoPdm.seashellinc.presentation.navigation.RegisterScreenSerializable
import com.proyectoPdm.seashellinc.presentation.ui.components.AppButton
import com.proyectoPdm.seashellinc.presentation.ui.components.LogoComponent
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars,
        containerColor = Background,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(MainBlue)
                ) {
                    LogoComponent(Modifier.size(60.dp), 1f)
                    Text(
                        "Shell's Calculator",
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 32.sp),
                        color = Buff
                    )
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = navigationBarHeight)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(MainBlue)
                ) { }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(MainBlue)
                ) {}
            }
        },
        floatingActionButton = {
            IconButton(
                onClick = {},
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(MainBlue)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Ayuda",
                    tint = Buff,
                    modifier = Modifier.size(45.dp)
                )
            }
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.seashelllogo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 150.dp, y = (225).dp),
                alpha = 0.2f
            )
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(10.dp))
            Text(
                "Bienvenido a SeaShell\nCalculator",
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp)
            )
            Spacer(Modifier.height(60.dp))
            AppButton("Unidades físicas\nde concentración", 240.dp) {
                navController.navigate(PhysicalUnitsScreenSerializable)
            }
            Spacer(Modifier.height(20.dp))
            AppButton("Unidades químicas\nde concentración", 240.dp) {
                navController.navigate(ChemicalUnitsScreenSerializable)
            }
            Spacer(Modifier.height(20.dp))
            AppButton("Lista de masas\nmolares", 240.dp) {
                navController.navigate(MolarMassScreenSerializable)
            }
            Spacer(Modifier.height(20.dp))
            AppButton("Balanceador de\necuaciones químicas", 240.dp, true) {
                /*if (user.IsPremium) */navController.navigate(BalEquationScreenSerializable) /*else navController.navigate(BuyPremiumScreenSerializable)*/
            }
            Spacer(Modifier.height(20.dp))
            AppButton("Tabla Periódica", 240.dp, true) {
                /*if (user.IsPremium) */ navController.navigate(PeriodicTableScreenSerializable) /*else navController.navigate(BuyPremiumScreenSerializable)*/
            }
            Spacer(Modifier.height(50.dp))

            //if(userIsLogged){ //TODO: implementar lógica de inicio de sesión
            //      Row(verticalAlignment = Alignment.CenterVertically) {
            //                IconButton(
            //                    onClick = {},
            //                    modifier = Modifier.size(50.dp),
            //                    colors = IconButtonDefaults.iconButtonColors(MainBlue)
            //                ) {
            //                    Icon(
            //                        imageVector = Icons.Outlined.AccountCircle,
            //                        contentDescription = "",
            //                        tint = Buff,
            //                        modifier = Modifier.size(45.dp)
            //                    )
            //                }
            //                Spacer(Modifier.width(10.dp))
            //                Text(
            //                    "UserName",//TODO: poner email de usuario
            //                    fontFamily = MontserratFontFamily,
            //                    fontWeight = FontWeight.Bold,
            //                    color = CitrineBrown,
            //                    fontSize = 20.sp
            //                )
            //            }
            // } else {

            Button(
                onClick = { navController.navigate(LoginScreenSerializable) },
                modifier = Modifier
                    .width(170.dp)
                    .border(1.dp, MainBlue, RoundedCornerShape(5.dp)),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Text(
                    "Iniciar Sesión",
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = CitrineBrown,
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            //}
        }
    }
}






