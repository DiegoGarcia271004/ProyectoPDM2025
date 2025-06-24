package com.proyectoPdm.seashellinc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.proyectoPdm.seashellinc.presentation.ui.screens.chemicalEquationBalancer.EquationBalancerScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.ChemicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.compounds.CompoundScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.LoadingScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.LoginScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.MainScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassPersonalScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.PeriodicTable.PeriodicTableScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.PhysicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.RegisterScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.physicalCalculatorsScreens.MassOverMassCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.physicalCalculatorsScreens.MassOverVolumeCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.physicalCalculatorsScreens.PartsPerMillionCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.physicalCalculatorsScreens.VolumeOverVolumeCalculator

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
            EquationBalancerScreen(navController)
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

        composable<CompoundScreenSerializable>{ compoundName ->
            val args = compoundName.toRoute<CompoundScreenSerializable>()
            CompoundScreen(navController, compoundName = args.compoundName, args.static)
        }

        composable<MassOverMassCalculatorSerializable> {
            MassOverMassCalculator(navController)
        }

        composable<MassOverVolumeCalculatorSerializable> {
            MassOverVolumeCalculator(navController)
        }

        composable<PartsPerMillionCalculatorSerializable> {
            PartsPerMillionCalculator(navController)
        }

        composable<VolumeOverVolumeCalculatorSerializable> {
            VolumeOverVolumeCalculator(navController)
        }
    }
}
