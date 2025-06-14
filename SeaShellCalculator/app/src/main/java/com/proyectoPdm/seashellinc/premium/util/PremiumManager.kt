package com.proyectoPdm.seashellinc.premium.util

import android.content.Context
import com.proyectoPdm.seashellinc.premium.data.PremiumRepository

object PremiumManager {
    private lateinit var repository: PremiumRepository

    fun initialize(context: Context) {
        repository = PremiumRepository(context)
    }

    fun isPremium(): Boolean {
        return repository.isUserPremium()
    }

    fun setPremium(status: Boolean) {
        repository.setPremiumStatus(status)
    }
}
