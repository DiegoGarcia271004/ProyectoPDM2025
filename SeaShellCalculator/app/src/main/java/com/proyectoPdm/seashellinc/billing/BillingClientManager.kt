package com.proyectoPdm.seashellinc.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import com.proyectoPdm.seashellinc.premium.util.PremiumManager

class BillingClientManager(
    private val context: Context,
    private val onPurchaseComplete: (Boolean) -> Unit
) : PurchasesUpdatedListener {

    private val billingClient: BillingClient

    init {
        billingClient = BillingClient.newBuilder(context)
            .enablePendingPurchases()
            .setListener(this)
            .build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {}
            override fun onBillingSetupFinished(billingResult: BillingResult) {}
        })
    }

    fun launchPurchaseFlow(activity: Activity, productId: String) {
        val queryParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                )
            ).build()

        billingClient.queryProductDetailsAsync(queryParams) { _, products ->
            val product = products.firstOrNull()
            product?.let {
                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(
                        listOf(
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(it)
                                .build()
                        )
                    ).build()
                billingClient.launchBillingFlow(activity, billingFlowParams)
            }
        }
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            purchases.forEach { purchase ->
                if (purchase.products.contains("premium")) {
                    PremiumManager.setPremium(true)
                    onPurchaseComplete(true)
                }
            }
        } else {
            onPurchaseComplete(false)
        }
    }
}
