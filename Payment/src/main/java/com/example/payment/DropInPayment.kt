package com.example.payment

import android.app.Activity
import android.content.Intent
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import com.braintreepayments.api.models.ThreeDSecureAdditionalInformation
import com.braintreepayments.api.models.ThreeDSecurePostalAddress
import com.braintreepayments.api.models.ThreeDSecureRequest

object DropInPayment {
    private val REQUEST_CODE = 101

    // Activity instance
    private var activity: Activity? = null

    /**
     * Drop in Listener
     */
    private var onDropInListener: OnDropInListener? = null
    private var braintreeClientToken: String? = null

    fun DropInPayment(
        activity: Activity?,
        onDropInListener: OnDropInListener?,
        brainTreeClientToken: String?
    ) {
        this.activity = activity
        this.onDropInListener = onDropInListener
        this.braintreeClientToken = braintreeClientToken
    }

    fun callBrainTreeUI(payableWalletAmount: String, userFirstName: String, userPhoneNumber: String) {
        val dropInRequest = DropInRequest()
            .requestThreeDSecureVerification(true)
            .threeDSecureRequest(threeDSecureRequest(payableWalletAmount, userFirstName, userPhoneNumber))
            .clientToken(braintreeClientToken)
        activity!!.startActivityForResult(
            dropInRequest.getIntent(activity!!.applicationContext),
            REQUEST_CODE
        )
    }

    /**
     * This ThreeDSecureRequest for Custom Ui
     * It may differ for Custom UI
     * @return ThreeDSecureRequest For 3D Secure
     */
    fun threeDSecureRequest(
        amount: String,
        firstName: String,
        phoneNumber: String
    ): ThreeDSecureRequest {
        val address = ThreeDSecurePostalAddress()
            .givenName(firstName)
            .phoneNumber(phoneNumber)

        val additionalInformation = ThreeDSecureAdditionalInformation()
            .shippingAddress(address)

        return ThreeDSecureRequest()
            .amount(amount)
            .billingAddress(address)
            .versionRequested(ThreeDSecureRequest.VERSION_2)
            .additionalInformation(additionalInformation)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                val result =
                    data.getParcelableExtra<DropInResult>(DropInResult.EXTRA_DROP_IN_RESULT)
                onDropInListener!!.OnDropInSuccess(result?.paymentMethodNonce!!.nonce.toString())
            }
        }
    }

    /**
     * Interface to listen the Drop in
     */
    interface OnDropInListener {
        fun OnDropInSuccess(paymentMethodNonce: String?)
    }
}