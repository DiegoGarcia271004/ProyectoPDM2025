package com.proyectoPdm.seashellinc.presentation.ui.components


import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.proyectoPdm.seashellinc.data.model.Element
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily
import com.proyectoPdm.seashellinc.presentation.ui.theme.getCategoryColor
import com.proyectoPdm.seashellinc.utils.getSpanishName
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue

@Composable
fun ElementDialog(element: Element?, changeShowDialog: (Element?) -> Unit) {
    AlertDialog(
        containerColor = Background,
        onDismissRequest = { changeShowDialog(element) },
        confirmButton = {
            TextButton(onClick = { changeShowDialog(element) }) {
                Text(
                    "Cerrar",
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
            }
        },
        title = {
            Text(
                text = element?.name.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = MontserratFontFamily,
                color = MainBlue
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Text(
                        "Símbolo: ",
                        fontFamily = MontserratFontFamily,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        element?.symbol.toString(),
                        fontFamily = MontserratFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CitrineBrown
                    )
                }
                Row {
                    Text(
                        "Categoría: ",
                        fontFamily = MontserratFontFamily,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        element?.category?.getSpanishName().toString(),
                        fontFamily = MontserratFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CitrineBrown
                    )
                }
                Row {
                    Text(
                        "Número Atómico: ",
                        fontFamily = MontserratFontFamily,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        element?.atomicNumber.toString(),
                        fontFamily = MontserratFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CitrineBrown
                    )

                }
                Row {
                    Text(
                        "Masa Atómica: ",
                        fontFamily = MontserratFontFamily,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        element?.atomicMass.toString(),
                        fontFamily = MontserratFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CitrineBrown
                    )
                }
            }
        }
    )
}