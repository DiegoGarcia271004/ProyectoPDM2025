package com.proyectoPdm.seashellinc.presentation.ui.screens

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
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightDarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue

@Composable
fun BalEquationScreen(navController: NavController) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = navigationBarHeight),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .height(17.dp)
                        .background(LightDarkBlue)
                ) { }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(18.dp)
                        .background(DarkBlue)
                ) {}
            }
        }
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
            modifier = Modifier.fillMaxSize().padding(bottom = navigationBarHeight),
        ) {
            Spacer(Modifier.height(20.dp))
            Row {
                Spacer(Modifier.width(50.dp))
                AppGoBackButton(60.dp){
                    navController.popBackStack()
                }
            }
        }
    }
}