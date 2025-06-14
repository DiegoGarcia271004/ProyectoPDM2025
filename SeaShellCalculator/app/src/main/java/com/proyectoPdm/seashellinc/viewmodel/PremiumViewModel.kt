package com.proyectoPdm.seashellinc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.proyectoPdm.seashellinc.premium.util.PremiumManager

class PremiumViewModel(application: Application) : AndroidViewModel(application) {

    private val _isPremium = MutableLiveData<Boolean>()
    val isPremium: LiveData<Boolean> = _isPremium

    init {
        PremiumManager.initialize(application)
        _isPremium.value = PremiumManager.isPremium()
    }

    fun updatePremiumStatus(status: Boolean) {
        PremiumManager.setPremium(status)
        _isPremium.value = status
    }
}
