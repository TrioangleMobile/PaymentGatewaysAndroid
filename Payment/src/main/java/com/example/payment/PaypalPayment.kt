package com.example.payment

import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.api.PayPal
import com.braintreepayments.api.models.PayPalRequest
import com.braintreepayments.api.models.PaymentMethodNonce
import com.example.payment.utils.NonceCreation

object PaypalPayment {

    // Activity instance
    private var activity: AppCompatActivity? = null

    private var braintreeClientToken: String? = null

    var payableAmount: String? = null

    var payableCurrency: String? = null

    private var paypalPaymentListener: OnPaypalPaymentListner? = null


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

        val nonceCreation = NonceCreation

        nonceCreation.initBraintree(braintreeClientToken!!, activity!!, paypalPaymentListener)

        val amount = payableAmount
        val currencycode = payableCurrency

        val request = PayPalRequest(amount)
            .currencyCode(currencycode)
            .intent(PayPalRequest.INTENT_SALE)
        PayPal.requestOneTimePayment(nonceCreation.mBraintreeFragment, request)
    }

}