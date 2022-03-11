package com.example.payment.utils

import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.api.BraintreeFragment
import com.braintreepayments.api.PayPal
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener
import com.braintreepayments.api.models.PayPalRequest
import com.braintreepayments.api.models.PaymentMethodNonce
import com.example.payment.PaypalPayment

object NonceCreation : PaymentMethodNonceCreatedListener {

    var mBraintreeFragment: BraintreeFragment? = null
    private var paypalPaymentListener: PaypalPayment.OnPaypalPaymentListner? = null


    fun initBraintree(braintreeClientToken: String, activity: AppCompatActivity, paypalPaymentListener: PaypalPayment.OnPaypalPaymentListner?) {
        val mAuthorization: String = braintreeClientToken

        try {
            this.paypalPaymentListener = paypalPaymentListener
            mBraintreeFragment = BraintreeFragment.newInstance(activity, mAuthorization)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // mBraintreeFragment is ready to use!

        mBraintreeFragment?.addListener(this)

        val amount = PaypalPayment.payableAmount
        val currencycode = PaypalPayment.payableCurrency

        setupBraintreeAndStartExpressCheckout(amount!!, currencycode!!)
    }


    fun setupBraintreeAndStartExpressCheckout(amount: String, currencycode: String) {
        val request = PayPalRequest(amount)
            .currencyCode(currencycode)
            .intent(PayPalRequest.INTENT_SALE)
        PayPal.requestOneTimePayment(mBraintreeFragment, request)
    }

    override fun onPaymentMethodNonceCreated(paymentMethodNonce: PaymentMethodNonce?) {
        paypalPaymentListener!!.OnPaypalPaymentComplete(paymentMethodNonce!!.toString()) //paykey
    }

    fun destroyBraintreeFragment() {
        try {
            mBraintreeFragment?.removeListener(this)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}