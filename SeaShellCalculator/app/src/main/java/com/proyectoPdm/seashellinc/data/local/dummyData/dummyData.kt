package com.proyectoPdm.seashellinc.data.local.dummyData

import com.proyectoPdm.seashellinc.data.model.Compound
import com.proyectoPdm.seashellinc.data.model.CompoundApiResponse
import com.proyectoPdm.seashellinc.data.model.CompoundResponse

object FakeRepository {
    val apiResponse = CompoundApiResponse(
        message = listOf(
            CompoundResponse("agua", "H2O", 18.01528),
            CompoundResponse("dioxido de carbono", "CO2", 44.0095),
            CompoundResponse("metano", "CH4", 16.04246),
            CompoundResponse("etanol", "C2H5OH", 46.06844)
        ),
        status = "success"
    )
}