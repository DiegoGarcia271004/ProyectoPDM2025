package com.proyectoPdm.seashellinc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.compose.ui.platform.LocalContext
import com.proyectoPdm.seashellinc.presentation.ui.screens.BalEquationScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.ChemicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.LoadingScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.LoginScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.MainScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.MolarMassPersonalScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.MolarMassScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.PeriodicTable.PeriodicTableScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.PhysicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.RegisterScreen
import com.proyectoPdm.seashellinc.premium.ui.PremiumFeaturesScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.BuyPremiumScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoadingScreenSerializable){

        composable<LoadingScreenSerializable> {
            LoadingScreen(navController)
        }

        composable<MainScreenSerializable>{
            MainScreen(navController)
        }

        composable<PhysicalUnitsScreenSerializable> {
            PhysicalUnitsScreen(navController)
        }

        composable<ChemicalUnitsScreenSerializable> {
            ChemicalUnitsScreen(navController)
        }

        composable<MolarMassScreenSerializable> {
            MolarMassScreen(navController)
        }

        composable<MolarMassPersonalScreenSerializable> {
            MolarMassPersonalScreen(navController)
        }

        composable<BalEquationScreenSerializable> {
            BalEquationScreen(navController)
        }

        composable<PeriodicTableScreenSerializable> {
            PeriodicTableScreen(navController)
        }

        composable<LoginScreenSerializable> {
            LoginScreen(navController)
        }

        composable<RegisterScreenSerializable> {
            RegisterScreen(navController)
        }

        composable("premium_features") {
            PremiumFeaturesScreen(navController)
        }

        composable("buy_premium") {
            val context = LocalContext.current
            val activity = context as android.app.Activity
            BuyPremiumScreen(navController, activity)
        }



    }
}