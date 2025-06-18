package com.proyectoPdm.seashellinc.presentation.ui.screens.calculatorsPhysicalUnits

import androidx.lifecycle.ViewModel
import com.proyectoPdm.seashellinc.data.local.model.Calculator
import com.proyectoPdm.seashellinc.data.local.model.Calculator.round
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//TODO: If possible, figure out how to solve the floating point rounding issue

@HiltViewModel
class PhysicalCalculatorViewModel @Inject constructor(): ViewModel() {
    private val _solute = MutableStateFlow<String>("")
    val solute: StateFlow<String> = _solute

    private val _solvent = MutableStateFlow<String>("")
    val solvent: StateFlow<String> = _solvent

    private val _concentration = MutableStateFlow<String>("")
    val concentration: StateFlow<String> = _concentration

    fun onSoluteChange(solute: String){
        _solute.value = solute
    }

    fun onSolventChange(solvent: String){
        _solvent.value = solvent
    }

    fun onConcentrationChange(concentration: String){
        _concentration.value = concentration
    }

    fun calculateRequiredSolute(): String{
        val solventVal = _solvent.value.toDoubleOrNull()
        val concentrationVal =_concentration.value.toDoubleOrNull()

        if (
            solventVal != null && concentrationVal != null
            && concentrationVal < 100.0 // esto de aquí es para prevenir división entre cero
        ){
            _solute.value = Calculator.calculateSolute(solventVal, concentrationVal).round(4).toString()
        } else {
            _solute.value = ""
        }
        return _solute.value
    }

    fun calculateRequiredSolvent(): String{
        val soluteVal =_solute.value.toDoubleOrNull()
        val concentrationVal =_concentration.value.toDoubleOrNull()

        if (
            soluteVal != null && concentrationVal != null
            && concentrationVal != 0.0 // otra división entre cero
        ){
            _solvent.value = Calculator.calculateSolvent(soluteVal, concentrationVal).round(4).toString()

        } else {
            _solvent.value = ""
        }
        return _solvent.value
    }

    fun calculateConcentrationPercentage(): String{
        val soluteVal =_solute.value.toDoubleOrNull()
        val solventVal =_solvent.value.toDoubleOrNull()

        if (
            solventVal != null && soluteVal != null
            && (solventVal + soluteVal) != 0.0 // uno u otro pueden ser cero, pero no ambos, pq sino hay división entre cero
        ){
            _concentration.value = Calculator.calculateConcentration(soluteVal, solventVal).round(4).toString()
        } else {
            _concentration.value = ""
        }
        return _concentration.value
    }

    fun clearAllInputs() {
        _solute.value = ""
        _solvent.value = ""
        _concentration.value = ""
    }
}