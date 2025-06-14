package com.proyectoPdm.seashellinc.premium.data

import android.content.Context
import android.content.SharedPreferences

class PremiumRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun isUserPremium(): Boolean {
        return prefs.getBoolean("is_premium", false)
    }

    fun setPremiumStatus(isPremium: Boolean) {
        prefs.edit().putBoolean("is_premium", isPremium).apply()
    }
}
