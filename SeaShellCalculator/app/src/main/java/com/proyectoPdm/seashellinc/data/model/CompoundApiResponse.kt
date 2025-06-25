package com.proyectoPdm.seashellinc.data.model

import com.google.gson.annotations.SerializedName

data class CompoundApiResponse(
    @SerializedName("message") val message : List<CompoundResponse>,
    @SerializedName("status") val status : String
)