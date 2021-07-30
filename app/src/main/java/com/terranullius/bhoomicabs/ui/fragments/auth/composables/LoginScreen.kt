package com.terranullius.bhoomicabs.ui.fragments.auth.composables

import android.text.TextUtils
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.terranullius.bhoomicabs.R
import com.terranullius.bhoomicabs.ui.composables.theme.BhoomicabsTheme
import com.terranullius.bhoomicabs.ui.composables.theme.lightBlueHeadline
import com.terranullius.bhoomicabs.ui.composables.theme.primaryDisabled
import com.terranullius.bhoomicabs.ui.viewmodels.AuthViewModel
import com.terranullius.bhoomicabs.ui.viewmodels.BaseViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent

@Composable
fun LoginScreen(viewModel: AuthViewModel, modifier: Modifier = Modifier) {

    LaunchedEffect(key1 = Unit) {
        viewModel.showNumbersHint()
    }

    BhoomicabsTheme {
        Surface() {
            Column(modifier) {
                //TODO CHANGE TO IMAGE
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    factory = {
                        LottieAnimationView(it).apply {
                            setAnimation(R.raw.login)
                            repeatCount = LottieDrawable.INFINITE
                            repeatMode = LottieDrawable.RESTART
                        }
                    }
                ) {
                    it.playAnimation()
                }

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    var isContinuable by remember {
                        mutableStateOf(false)
                    }

                    Text(
                        text = "LOGIN", style = MaterialTheme.typography.h6.copy(
                            color = lightBlueHeadline
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Enter your phone number to proceed")
                    Spacer(modifier = Modifier.height(24.dp))

                    val phoneNumber = BaseViewModel.phoneNumberStateFlow.collectAsState(0L)
                    isContinuable = phoneNumber.value.toString().length == 10

                    val phoneNumberError = remember {
                        mutableStateOf(false)
                    }

                    PhoneNumberField(
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { newValue->
                            if (isValidNumber(newValue)) {
                                newValue.toLongOrNull()?.let {
                                    viewModel.setNumber(it)
                                } ?: viewModel.setNumber(0L)
                                phoneNumberError.value = false
                            } else {
                                phoneNumberError.value = true
                            }
                        },
                        value = if (phoneNumber.value == 0L) "" else phoneNumber.value.toString() , isError = phoneNumberError
                    ) {
                        login(phoneNumber.value.toString(), viewModel)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (!isContinuable) primaryDisabled else MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onPrimary
                        ),
                        onClick = { login(phoneNumber.value.toString(), viewModel) }
                    ) {
                        Text(text = if (!isContinuable) "ENTER PHONE NUMBER" else "CONTINUE")
                    }
                }
            }
        }
    }
}

private fun isValidNumber(number: String) =
    (number.length <= 10 && containsOnlyNumbers(number) || number.isEmpty())

fun containsOnlyNumbers(number: String): Boolean {
    return TextUtils.isDigitsOnly(number)
}

fun login(number: String, viewModel: AuthViewModel) {
    if (isValidNumber(number)) {
        if (number.length == 10) {
            number.toLongOrNull()?.let {
                viewModel.startVerification(it)
                viewModel.navigate(NavigationEvent.LoginToOtp)
            }
        }
    }
}