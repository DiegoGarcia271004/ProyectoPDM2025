package com.proyectoPdm.seashellinc.presentation.ui.screens.PeriodicTable

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyectoPdm.seashellinc.data.local.elements
import com.proyectoPdm.seashellinc.data.local.model.Element
import com.proyectoPdm.seashellinc.presentation.ui.components.AppGoBackButton
import com.proyectoPdm.seashellinc.presentation.ui.components.ElementCard
import com.proyectoPdm.seashellinc.presentation.ui.components.LogoComponent
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightDarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily

@Composable
fun PeriodicTableScreen(navController: NavController/*periodicTableViewModel: PeriodicTableViewModel*/) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    // TODO Esta parte se debe de poner en el viewModel
    //TODO Hasta aqui
    val elements: List<Element> = elements
    val maxPeriod = elements.maxOf { it.period }
    val maxGroup = 19

    val table = Array(maxPeriod + 1) { Array<Element?>(maxGroup + 1) { null } }
    elements.forEach { element ->
        table[element.period][element.group] = element
    }

    val tableByGroup = (1..maxGroup).map { group ->
        (1..maxPeriod).map { period ->
            table[period][group]
        }
    }

    Scaffold(
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
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(MainBlue),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LogoComponent(modifier = Modifier.size(60.dp), 1f)
                }
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()) {
                    Spacer(Modifier.width(10.dp))
                    AppGoBackButton(75.dp) {
                        navController.popBackStack()
                    }
                    Text(
                        "Tabla Periódica\nde los elementos",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = CitrineBrown,
                        fontSize = 20.sp
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 600.dp)
                        ) {
                            val scrollState = rememberScrollState()

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(scrollState)
                                    .padding(8.dp)
                            ) {
                                tableByGroup.forEachIndexed { groupIndex, column ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        //modifier = Modifier.padding(horizontal = 4.dp)
                                    ) {
                                        column.forEachIndexed { periodIndex, element ->
                                            if (periodIndex == 7) {
                                                Spacer(modifier = Modifier.height(16.dp))
                                            }
                                            if (element != null) {
                                                ElementCard(element = element, onClick = {})
                                            } else {
                                                Spacer(modifier = Modifier.size(75.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}