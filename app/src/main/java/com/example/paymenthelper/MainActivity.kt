package com.example.paymenthelper

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.payment.PaypalPayment
import com.example.payment.StripePayment

class MainActivity : AppCompatActivity(), PaypalPayment.OnPaypalPaymentListner {

    var braintreeClientToken: String? = null
    private var paypalPayment: PaypalPayment? = null
    private var stripePayment: StripePayment? = null

    lateinit var payWithPaypal: Button
    lateinit var payWithStripe: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initPaypal()

        //initStripe()
    }

    fun initStripe() {
        stripePayment = StripePayment

        stripePayment!!.StripePayment(this, "")

        stripePayment!!.initStripeData()

        payWithPaypal = findViewById(R.id.btn_stripe)
        payWithStripe.setOnClickListener {
            stripePayment!!.getClientSecret("", this, "", "Error")
        }
    }

    fun initPaypal() {
        braintreeClientToken =
            "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJleUowZVhBaU9pSktWMVFpTENKaGJHY2lPaUpGVXpJMU5pSXNJbXRwWkNJNklqSXdNVGd3TkRJMk1UWXRjMkZ1WkdKdmVDSXNJbWx6Y3lJNkltaDBkSEJ6T2k4dllYQnBMbk5oYm1SaWIzZ3VZbkpoYVc1MGNtVmxaMkYwWlhkaGVTNWpiMjBpZlEuZXlKbGVIQWlPakUyTkRjd09EazBNRFVzSW1wMGFTSTZJbU5sTTJNNU16RTJMV0kwTjJFdE5EUmpNaTFpTlRSbExUWmtZelZoTW1ZNE1UYzBOaUlzSW5OMVlpSTZJbVEzT0RVeWNYWjJkelozYWpJM04yMGlMQ0pwYzNNaU9pSm9kSFJ3Y3pvdkwyRndhUzV6WVc1a1ltOTRMbUp5WVdsdWRISmxaV2RoZEdWM1lYa3VZMjl0SWl3aWJXVnlZMmhoYm5RaU9uc2ljSFZpYkdsalgybGtJam9pWkRjNE5USnhkblozTm5kcU1qYzNiU0lzSW5abGNtbG1lVjlqWVhKa1gySjVYMlJsWm1GMWJIUWlPbVpoYkhObGZTd2ljbWxuYUhSeklqcGJJbTFoYm1GblpWOTJZWFZzZENKZExDSnpZMjl3WlNJNld5SkNjbUZwYm5SeVpXVTZWbUYxYkhRaVhTd2liM0IwYVc5dWN5STZleUpqZFhOMGIyMWxjbDlwWkNJNklqRXdNRGN3T1RnM05qVTBNekl4TURRaWZYMC5MeDBRZWk4RXNSWUp2T2Fvb1JCcFVlSGJ3SzJFby03OGgxeUdBTTFVTHdHaWNxZmVwWmVMdk51Y2RkMnR6eWQzMGFySzVOMzQ4OGc0RjYzUWl2TnhfQT9jdXN0b21lcl9pZD0iLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvZDc4NTJxdnZ3NndqMjc3bS9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJncmFwaFFMIjp7InVybCI6Imh0dHBzOi8vcGF5bWVudHMuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9ncmFwaHFsIiwiZGF0ZSI6IjIwMTgtMDUtMDgiLCJmZWF0dXJlcyI6WyJ0b2tlbml6ZV9jcmVkaXRfY2FyZHMiXX0sImhhc0N1c3RvbWVyIjp0cnVlLCJjbGllbnRBcGlVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvZDc4NTJxdnZ3NndqMjc3bS9jbGllbnRfYXBpIiwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwibWVyY2hhbnRJZCI6ImQ3ODUycXZ2dzZ3ajI3N20iLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbSIsInZlbm1vIjoib2ZmIiwiY2hhbGxlbmdlcyI6W10sInRocmVlRFNlY3VyZUVuYWJsZWQiOmZhbHNlLCJhbmFseXRpY3MiOnsidXJsIjoiaHR0cHM6Ly9vcmlnaW4tYW5hbHl0aWNzLXNhbmQuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9kNzg1MnF2dnc2d2oyNzdtIn0sInBheXBhbEVuYWJsZWQiOnRydWUsInBheXBhbCI6eyJiaWxsaW5nQWdyZWVtZW50c0VuYWJsZWQiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjpmYWxzZSwidW52ZXR0ZWRNZXJjaGFudCI6ZmFsc2UsImFsbG93SHR0cCI6dHJ1ZSwiZGlzcGxheU5hbWUiOiJKb2huIERvZSdzIFRlc3QgU3RvcmUiLCJjbGllbnRJZCI6IkFiYVhTaEhLQ3hrUWVqcHlPRTBWVmFzZVM0aXVWei1oeUxKUTZfTGZoY1hhUVZPTm9iTjJ2NmI1OFNYUTVJc0p0djQ2dWk0aTVLVEpVTHNPIiwicHJpdmFjeVVybCI6Imh0dHBzOi8vZXhhbXBsZS5jb20iLCJ1c2VyQWdyZWVtZW50VXJsIjoiaHR0cHM6Ly9leGFtcGxlLmNvbSIsImJhc2VVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFzc2V0c1VybCI6Imh0dHBzOi8vY2hlY2tvdXQucGF5cGFsLmNvbSIsImRpcmVjdEJhc2VVcmwiOm51bGwsImVudmlyb25tZW50Ijoib2ZmbGluZSIsImJyYWludHJlZUNsaWVudElkIjoibWFzdGVyY2xpZW50MyIsIm1lcmNoYW50QWNjb3VudElkIjoiVVNEIiwiY3VycmVuY3lJc29Db2RlIjoiVVNEIn19"

        paypalPayment = PaypalPayment

        paypalPayment!!.PaypalPayment(this, braintreeClientToken!!, "20", "USD", this)

        payWithPaypal = findViewById(R.id.btn_paypal)
        payWithPaypal.setOnClickListener {
            try {
                PaypalPayment.payWithPaypal()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun OnPaypalPaymentComplete(paymentMethodNonce: String?) {
        paymentMethodNonce
    }

    override fun onDestroy() {
        super.onDestroy()
        paypalPayment!!.destroyBraintreeFragment()
    }
}