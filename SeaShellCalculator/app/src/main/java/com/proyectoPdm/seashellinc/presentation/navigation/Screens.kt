package com.proyectoPdm.seashellinc.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object PeriodicTableScreenSerializable

@Serializable
object BalEquationScreenSerializable

@Serializable
object BuyPremiumScreenSerializable

@Serializable
object ChemicalUnitsScreenSerializable

@Serializable
data class CompoundScreenSerializable(val compoundName : String, val static : Boolean = true)

@Serializable
object LoadingScreenSerializable

@Serializable
object LoginScreenSerializable

@Serializable
object MainScreenSerializable

@Serializable
object MolarMassPersonalScreenSerializable

@Serializable
object MolarMassScreenSerializable

@Serializable
object PhysicalUnitsScreenSerializable

@Serializable
object RegisterScreenSerializable

@Serializable
object MassOverMassCalculatorSerializable

@Serializable
object MassOverVolumeCalculatorSerializable

@Serializable
object PartsPerMillionCalculatorSerializable

@Serializable
object VolumeOverVolumeCalculatorSerializable
