package com.terranullius.bhoomicabs.ui

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.terranullius.bhoomicabs.R
import com.terranullius.bhoomicabs.other.Constants.CREDENTIAL__PHONE_PICKER_REQUEST
import com.terranullius.bhoomicabs.ui.viewmodels.MainViewModel
import com.terranullius.bhoomicabs.util.EventObserver
import com.terranullius.bhoomicabs.util.PaymentStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PaymentResultWithDataListener {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var checkout: Checkout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkout = Checkout()

        Checkout.preload(applicationContext)

        checkout.setKeyID("rzp_test_l5ciOf0wh13GfR")

        setContentView(R.layout.activity_main)
        setObservers()

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsVerificationReceiver, intentFilter, SmsRetriever.SEND_PERMISSION, null)
    }

    private fun setObservers(){

        viewModel.getNumberChooserEvent().observe(this, EventObserver {
            requestHint()
        })

        viewModel.getVerificationStartedLiveData().observe(this, EventObserver {
            val client = SmsRetriever.getClient(this)
            lifecycleScope.launch {
                val task = client.startSmsRetriever()
                task.addOnSuccessListener {
                    Log.d("sha", "listening SMS")
                }

                task.addOnFailureListener {
                    Log.d("sha", "sms retriever failed")
                }
            }
        })

        lifecycleScope.launchWhenCreated {
            viewModel.getPaymentStatus().collect {
                when(it){
                   is PaymentStatus.InitiateCheckout -> {
                       initiateCheckout(it.amount, it.orderId)
                   }
                    is PaymentStatus.Failed ->{
                        Toast.makeText(this@MainActivity, it.errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    private fun initiateCheckout(amount: Long, orderId: String) {

        try {
            val options = JSONObject()
            options.put("name", "Esskay")
            options.put("description", "Reference No. #123456")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("order_id", orderId) //from response of step 3.
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", "${amount*100}") //pass amount in currency subunits
            options.put("prefill.contact", "9334805466")
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)
            checkout.open(this@MainActivity, options)
        } catch (e: Exception) {
            viewModel.setPaymentStatus(
                PaymentStatus.Failed("Error starting razorpay checkout: ${e.message}")
            )
        }

    }


    private fun requestHint() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true) // this flag make sure that Selector get the phoneNumbers
            .build()
        val credentialsClient = Credentials.getClient(this)
        val intent = credentialsClient.getHintPickerIntent(hintRequest)
        startIntentSenderForResult(
            intent.intentSender,
            CREDENTIAL__PHONE_PICKER_REQUEST,
            null, 0, 0, 0
        )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CREDENTIAL__PHONE_PICKER_REQUEST ->
                // Obtain the phone number from the result
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val credential = data.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                    credential?.id;
                    credential?.id?.let { viewModel.setNumber(processNumber(it)) } ?: return

                    Log.d("fuck", "credential: ${credential.id}")
                }

        }
    }
    private fun processNumber(id: String): Long {
        var number = 0L
        if (id.contains('+')){
            number = id.substring(3).replace("-", "").replace(" ", "").toLongOrNull() ?: 0L
        } else if (id.startsWith('0')){
            number = id.substring(1).replace("-", "").replace(" ", "").toLongOrNull() ?: 0L
        }
        return number
    }

    private val smsVerificationReceiver = object : BroadcastReceiver() {
          override fun onReceive(context: Context?, intent: Intent) {
              if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                  val extras = intent.extras
                  val status = extras!![SmsRetriever.EXTRA_STATUS] as Status?
                  when (status!!.statusCode) {
                      CommonStatusCodes.SUCCESS ->{
                          val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String? //message from sms
                          Log.d("fuck", "SMS $message")
                      }
                      CommonStatusCodes.TIMEOUT -> {
                      }
                  }
              }
          }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?, data: PaymentData?) {
        data?.let { paymentData ->
            val orderId = paymentData.orderId
            val amount = paymentData.data.getString("amount")

            Log.d("shit", "paid amount: $amount")

            viewModel.updatePayment(orderId, amount)
        }
    }

    override fun onPaymentError(errorCode: Int, response: String?, data: PaymentData?) {

    }


}