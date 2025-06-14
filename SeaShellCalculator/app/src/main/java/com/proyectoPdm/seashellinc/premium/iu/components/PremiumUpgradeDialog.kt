package com.proyectoPdm.seashellinc.premium.iu.components

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun PremiumUpgradeDialog(onDismiss: () -> Unit = {}, onUpgrade: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Función Premium") },
        text = { Text("Esta funcionalidad está disponible solo para usuarios Premium.") },
        confirmButton = {
            TextButton(onClick = onUpgrade) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
