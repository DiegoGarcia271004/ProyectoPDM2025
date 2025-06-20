package com.proyectoPdm.seashellinc.data.model

import com.google.gson.annotations.SerializedName

class CompoundResponse(
    @SerializedName("compoundName") val compoundName: String,
    @SerializedName("chemicalFormula") val chemicalFormula: String,
    @SerializedName("molarMass") val molarMass: Double,
) {
    fun toCompound() = Compound(compoundName, chemicalFormula, molarMass)
}