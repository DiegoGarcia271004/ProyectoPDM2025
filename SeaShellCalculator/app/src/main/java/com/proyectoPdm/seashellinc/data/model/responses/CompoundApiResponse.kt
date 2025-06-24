package com.proyectoPdm.seashellinc.data.model.responses

import com.google.gson.annotations.SerializedName
import com.proyectoPdm.seashellinc.data.model.responses.CompoundResponse

data class CompoundApiResponse(
    @SerializedName("message") val message : List<CompoundResponse>,
    @SerializedName("status") val status : String
)