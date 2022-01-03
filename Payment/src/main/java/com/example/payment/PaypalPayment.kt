package com.example.payment

import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.api.BraintreeFragment
import com.braintreepayments.api.PayPal
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener
import com.braintreepayments.api.models.PayPalRequest
import com.braintreepayments.api.models.PaymentMethodNonce

object PaypalPayment : PaymentMethodNonceCreatedListener {

    // Activity instance
    private var activity: AppCompatActivity? = null

    private var braintreeClientToken: String? = null

    var payableAmount: String? = null

    var payableCurrency: String? = null

    private var mBraintreeFragment: BraintreeFragment? = null
    private var paypalPaymentListener: OnPaypalPaymentListner? = null

    /**
     * Interface to listen the Facebook login
     */
    interface OnPaypalPaymentListner {
        fun OnPaypalPaymentComplete(paymentMethodNonce: PaymentMethodNonce?)
    }

    fun PaypalPayment(
        activity: AppCompatActivity,
        braintreeClientToken: String,
        payableAmount: String,
        payableCurrency: String
    ) {
        this.activity = activity
        this.braintreeClientToken = braintreeClientToken
        this.payableAmount = payableAmount
        this.payableCurrency = payableCurrency
    }

    /**
     * pay With paypal with mode and id
     */
    fun payWithPaypal() {

        initiateBraintree(braintreeClientToken!!)

        var amount = payableAmount
        var currencycode = payableCurrency

        val request = PayPalRequest(amount)
            .currencyCode(currencycode)
            .intent(PayPalRequest.INTENT_SALE)
        PayPal.requestOneTimePayment(mBraintreeFragment, request)
    }

    fun initiateBraintree(braintreeClientToken: String) {
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