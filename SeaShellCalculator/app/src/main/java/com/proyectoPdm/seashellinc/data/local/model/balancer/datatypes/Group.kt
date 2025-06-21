package com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes

import com.proyectoPdm.seashellinc.data.local.model.balancer.datatypes.FormulaItem
import com.proyectoPdm.seashellinc.data.local.model.balancer.ParseError
import com.proyectoPdm.seashellinc.utils.checkedAddSum
import com.proyectoPdm.seashellinc.utils.checkedMultiply

data class Group(val items: List<FormulaItem>, val count: Int): FormulaItem {
    init {
        if (count < 1){
            throw IllegalStateException("Assertion Error: Count para grupo debe ser un entero positivo, pero se obtuvo $count")
        }
        if (items.isEmpty()){
            throw ParseError("Grupo vacío.", -1)
        }
    }

    override fun getElements(resultSet: MutableSet<String>) {
        for (item in items){
            item.getElements(resultSet)
        }
    }

    override fun countElement(targetName: String): Long {
        var sum: Long = 0

        for (item in items) {
            sum = checkedAddSum(
                sum,
                checkedMultiply(item.countElement(targetName), this.count.toLong())
            )
        }
        return sum
    }

    fun toDisplayString(): String {
        val innerString = items.joinToString(separator = "") {
            when (it) {
                is ChemElem -> it.toDisplayString()
                is Group -> it.toDisplayString()
                else -> "" // En caso de tipos inesperados, aunque no debería ocurrir con FormulaItem
            }
        }
        return if (count == 1) "($innerString)" else "($innerString)$count"
    }

// toHtml() equivalente para Jetpack Compose:
// Similar a ChemElem, la lógica de renderizado de AnnotatedString residiría en un Composable.
//    fun toAnnotatedString(): AnnotatedString {
//        return buildAnnotatedString {
//            append("(")
//            for (item in items) {
//                append(
//                    when (item) {
//                        is ChemElem -> item.toAnnotatedString()
//                        is Group -> item.toAnnotatedString()
//                        else -> AnnotatedString("")
//                    }
//                )
//            }
//            append(")")
//            if (count != 1) {
//                withStyle(SpanStyle(baselineShift = BaselineShift.Subscript)) {
//                    append(count.toString())
//                }
//            }
//        }
//    }

}