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
            override fun onBillingServiceDisconnected() {
                billingClient.startConnection(this)
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    verifyExistingPurchases()
                }
            }
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

        billingClient.queryProductDetailsAsync(queryParams) { billingResult, products ->
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

    fun verifyExistingPurchases() {
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { result, purchases ->
            val isPremium = purchases.any { it.products.contains("premium") }
            PremiumManager.setPremium(isPremium)
            onPurchaseComplete(isPremium)
        }
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            purchases.forEach { purchase ->
                if (purchase.products.contains("premium")) {
                    if (!purchase.isAcknowledged) {
                        val params = AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.purchaseToken)
                            .build()

                        billingClient.acknowledgePurchase(params) { ackResult ->
                            if (ackResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                PremiumManager.setPremium(true)
                                onPurchaseComplete(true)
                            } else {
                                onPurchaseComplete(false)
                            }
                        }
                    } else {
                        PremiumManager.setPremium(true)
                        onPurchaseComplete(true)
                    }
                }
            }
        } else {
            onPurchaseComplete(false)
        }
    }
}
