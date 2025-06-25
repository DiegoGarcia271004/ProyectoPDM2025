package com.proyectoPdm.seashellinc.data.local.model

sealed interface CalculationResult {
    data class Success(val value: String) : CalculationResult
    data class Error(val value: String) : CalculationResult
    object Empty : CalculationResult
}