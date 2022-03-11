package com.example.payment

import androidx.appcompat.app.AppCompatActivity
import com.example.payment.utils.NonceCreation

object PaypalPayment {

    // Activity instance
    private var activity: AppCompatActivity? = null

    private var braintreeClientToken: String? = null

    var payableAmount: String? = null

    var payableCurrency: String? = null

    private var paypalPaymentListener: OnPaypalPaymentListner? = null

    private var nonceCreation: NonceCreation? = null


    /**
     * Interface to listen the Facebook login
     */
    interface OnPaypalPaymentListner {
        fun OnPaypalPaymentComplete(paymentMethodNonce: String?)
    }

    fun PaypalPayment(
        activity: AppCompatActivity,
        braintreeClientToken: String,
        payableAmount: String,
        payableCurrency: String,
        paypalPaymentListener: OnPaypalPaymentListner?
    ) {
        this.activity = activity
        this.braintreeClientToken = braintreeClientToken
        this.payableAmount = payableAmount
        this.payableCurrency = payableCurrency
        this.paypalPaymentListener = paypalPaymentListener
    }

    /**
     * pay With paypal with mode and id
     */
    fun payWithPaypal() {

        nonceCreation = NonceCreation

        nonceCreation!!.initBraintree(braintreeClientToken!!, activity!!, paypalPaymentListener)
    }


    fun destroyBraintreeFragment() {
        nonceCreation!!.destroyBraintreeFragment()
    }
}