package com.proyectoPdm.seashellinc.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.proyectoPdm.seashellinc.billing.BillingClientManager
import com.proyectoPdm.seashellinc.billing.PurchaseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BillingViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private lateinit var billingClientManager: BillingClientManager

    private val _purchaseStatus = MutableStateFlow<PurchaseStatus>(PurchaseStatus.Idle)
    val purchaseStatus: StateFlow<PurchaseStatus> = _purchaseStatus

    fun initBillingManager() {
        if (!::billingClientManager.isInitialized) {
            billingClientManager = BillingClientManager(getApplication()) { success ->
                _purchaseStatus.value = if (success) {
                    PurchaseStatus.Success
                } else {
                    PurchaseStatus.Error("Compra fallida o cancelada")
                }
            }
        }
    }

    fun launchPurchase(activity: Activity, productId: String) {
        _purchaseStatus.value = PurchaseStatus.Loading
        billingClientManager.launchPurchaseFlow(activity, productId)
    }

    fun resetStatus() {
        _purchaseStatus.value = PurchaseStatus.Idle
    }
}
