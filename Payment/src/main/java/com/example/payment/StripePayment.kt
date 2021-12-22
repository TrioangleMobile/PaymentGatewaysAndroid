package com.example.payment

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.payment.utils.JsonResponse
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import org.json.JSONObject

object StripePayment {

    // Activity instance
    private var context: Context? = null

    private var stripe: Stripe? = null

    var stripePublishKey: String? = null

    fun StripePayment(context: Context, stripePublishKey: String) {
        this.context = context
        this.stripePublishKey = stripePublishKey
    }

    /**
     * init Stripe
     */
    fun initStripeData() {
        PaymentConfiguration.init(context!!, stripePublishKey!!)
        stripe = Stripe(context!!, PaymentConfiguration.getInstance(context!!).publishableKey)
    }

    /**
     * Stripe Instance
     */
    fun stripeInstance(): Stripe? {
        return stripe
    }

    /**
     * Get Client Secret From Response
     */
    fun getClientSecret(
        jsonResponse: JsonResponse,
        activity: AppCompatActivity,
        privacyURL: String,
        errorMsg: String
    ) {
        val clientSecret =
            getJsonValue(jsonResponse.strResponse, "two_step_id", String::class.java) as String
        if (stripeInstance() != null) {
            stripeInstance()!!.confirmPayment(
                activity,
                createPaymentIntentParams(clientSecret, activity.applicationContext, privacyURL)
            )
        } else {
            Toast.makeText(
                activity.applicationContext,
                errorMsg,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Get Client Secret From Response
     */
    fun getClientSecret(
        twoStepId: String,
        activity: AppCompatActivity,
        privacyURL: String,
        errorMsg: String
    ) {

        if (stripeInstance() != null) {
            stripeInstance()!!.confirmPayment(
                activity,
                createPaymentIntentParams(twoStepId, activity.applicationContext, privacyURL)
            )
        } else {
            Toast.makeText(
                activity.applicationContext,
                errorMsg,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun getJsonValue(jsonString: String, key: String, `object`: Any): Any {
        var object1 = `object`
        try {
            val jsonObject = JSONObject(jsonString)
            if (jsonObject.has(key)) object1 = jsonObject.get(key)
        } catch (e: Exception) {
            e.printStackTrace()
            return Any()
        }

        return object1
    }

    /**
     * Create a Payment to Start Payment Process
     */
    fun createPaymentIntentParams(
        clientSecret: String,
        context: Context,
        privacyURL: String
    ): ConfirmPaymentIntentParams {
        return ConfirmPaymentIntentParams.create(
            clientSecret,
            privacyURL
        )
    }
}