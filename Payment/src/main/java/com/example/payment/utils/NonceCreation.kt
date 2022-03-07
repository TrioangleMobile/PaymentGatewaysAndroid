package com.example.payment.utils

import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.api.BraintreeFragment
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener
import com.braintreepayments.api.models.PaymentMethodNonce
import com.example.payment.PaypalPayment

object NonceCreation : PaymentMethodNonceCreatedListener {

    var mBraintreeFragment: BraintreeFragment? = null
    private var paypalPaymentListener: PaypalPayment.OnPaypalPaymentListner? = null

    fun initBraintree(braintreeClientToken: String, activity: AppCompatActivity) {
        val mAuthorization: String = braintreeClientToken

        try {
            mBraintreeFragment = BraintreeFragment.newInstance(activity, mAuthorization)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // mBraintreeFragment is ready to use!

        mBraintreeFragment?.addListener(this)
    }

    override fun onPaymentMethodNonceCreated(paymentMethodNonce: PaymentMethodNonce?) {
        paypalPaymentListener!!.OnPaypalPaymentComplete(paymentMethodNonce!!) //paykey
    }
}