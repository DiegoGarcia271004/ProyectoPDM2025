package com.proyectoPdm.seashellinc.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.Marigold
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily

@Composable
fun AppButton(
    text: String,
    width: Dp,
    isPremium: Boolean = false,
    onClick: () -> Unit
) {
    var modifier: Modifier = if (isPremium) {
        Modifier
            .width(width)
            .border(3.7.dp, Marigold, RoundedCornerShape(5.dp))
    } else {
        Modifier.width(width)
    }

    Button(
        onClick = {onClick()},
        colors = ButtonDefaults.buttonColors(MainBlue),
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
    ) {
        Text(
            text,
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.Bold,
            color = Buff,
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 15.sp)
        )
    }

}