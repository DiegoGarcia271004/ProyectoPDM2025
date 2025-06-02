package com.proyectoPdm.seashellinc.data.local.model

data class Element(
    val symbol: String,
    val name: String,
    val atomicNumber: Int,
    val group: Int,
    val period: Int,
    val category: ElementCategory
)