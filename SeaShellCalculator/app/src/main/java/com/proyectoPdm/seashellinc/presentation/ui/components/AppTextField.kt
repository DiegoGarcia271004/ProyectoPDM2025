package com.proyectoPdm.seashellinc.presentation.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily
import org.w3c.dom.Text


@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    var query by remember { mutableStateOf("") }

    val visualTransformation: VisualTransformation = if (isPassword) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    TextField(
        value = query,
        onValueChange = {query = it},
        label = {
            Text(label, fontFamily = MontserratFontFamily, fontWeight = FontWeight.Bold)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFD9D9D9),
            focusedContainerColor = Color(0xFFD9D9D9),
            unfocusedTextColor = MainBlue,
            focusedTextColor = MainBlue,
            focusedIndicatorColor = MainBlue,
            focusedLabelColor = MainBlue,
            cursorColor = MainBlue,
        ),
        modifier = Modifier.height(30.dp),
        visualTransformation = visualTransformation
    )
}