package com.proyectoPdm.seashellinc.premium.domain

import android.content.Context
import com.proyectoPdm.seashellinc.premium.data.PremiumRepository

class CheckPremiumStatusUseCase(context: Context) {
    private val repository = PremiumRepository(context)
    fun execute(): Boolean = repository.isUserPremium()
}
