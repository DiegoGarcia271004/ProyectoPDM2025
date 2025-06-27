package com.proyectoPdm.seashellinc.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.proyectoPdm.seashellinc.presentation.ui.screens.BuyPremiumScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.chemicalEquationBalancer.EquationBalancerScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.ChemicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.error.ErrorScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.compounds.CompoundScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.LoadingScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.LoginScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.MainScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassPersonalScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses.MolarMassScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.PeriodicTable.PeriodicTableScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.PhysicalUnitsScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.RegisterScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.error.ErrorViewModel
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits.molality.MolalityCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits.molarFraction.MolarFractionCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits.molarity.MolarityCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsChemicalUnits.normality.NormalityCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.physicalCalculatorsScreens.MassOverMassCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.physicalCalculatorsScreens.MassOverVolumeCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.physicalCalculatorsScreens.PartsPerMillionCalculator
import com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits.physicalCalculatorsScreens.VolumeOverVolumeCalculator

@Composable
fun Navigation(
    userViewModel: UserViewModel,
    errorViewModel : ErrorViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoadingScreenSerializable){

        composable<LoadingScreenSerializable> {
            LoadingScreen(navController)
        }

        composable<MainScreenSerializable>{
            MainScreen(navController, userViewModel, errorViewModel)
        }

        composable<PhysicalUnitsScreenSerializable> {
            PhysicalUnitsScreen(navController)
        }

        composable<ChemicalUnitsScreenSerializable> {
            ChemicalUnitsScreen(navController)
        }

        composable<MolarMassScreenSerializable> { backOfPremium ->
            val args = backOfPremium.toRoute<MolarMassScreenSerializable>()
            MolarMassScreen(navController, userViewModel =  userViewModel, errorViewModel = errorViewModel, backOfPremium = args.backOfPremium)
        }

        composable<MolarMassPersonalScreenSerializable> { backOfPremium ->
            val args = backOfPremium.toRoute<MolarMassPersonalScreenSerializable>()
            MolarMassPersonalScreen(navController, userViewModel =  userViewModel, backOfPremium =  args.backOfPremium)
        }

        composable<BalEquationScreenSerializable> { backOfPremium ->
            val args = backOfPremium.toRoute<BalEquationScreenSerializable>()
            EquationBalancerScreen(navController, backOfPremium =  args.backOfPremium)
        }

        composable<PeriodicTableScreenSerializable> { backOfPremium ->
            val args = backOfPremium.toRoute<PeriodicTableScreenSerializable>()
            PeriodicTableScreen(navController, backOfPremium = args.backOfPremium)
        }

        composable<LoginScreenSerializable> {
            LoginScreen(navController, userViewModel)
        }

        composable<RegisterScreenSerializable> {
            RegisterScreen(navController, userViewModel)
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

        composable<ErrorScreenSerializable> {
            ErrorScreen(navController, errorViewModel)
        }

        composable<BuyPremiumScreenSerializable> { screen ->
            val args = screen.toRoute<BuyPremiumScreenSerializable>()
            Log.d("Log en Navigation", args.screen)
            BuyPremiumScreen(navController, userViewModel, errorViewModel, args.screen)
        }

        composable<VolumeOverVolumeCalculatorSerializable> {
            VolumeOverVolumeCalculator(navController)
        }

        composable<MolarityCalculatorSerializable>(){
            MolarityCalculator(navController)
        }

        composable<MolalityCalculatorSerializable>(){
            MolalityCalculator(navController)
        }

        composable<NormalityCalculatorSerializable>(){
            NormalityCalculator(navController)
        }

        composable<MolarFractionCalculatorSerializable>(){
            MolarFractionCalculator(navController)
        }
    }
}
