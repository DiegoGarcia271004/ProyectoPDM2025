package com.proyectoPdm.seashellinc.data.model.responses

import com.google.gson.annotations.SerializedName
import com.proyectoPdm.seashellinc.data.model.compound.Compound

class CompoundResponse(
    @SerializedName("compoundName") val compoundName: String,
    @SerializedName("chemicalFormula") val chemicalFormula: String,
    @SerializedName("molarMass") val molarMass: Double,
) {
    fun toCompound() = Compound(compoundName, chemicalFormula, molarMass)
}