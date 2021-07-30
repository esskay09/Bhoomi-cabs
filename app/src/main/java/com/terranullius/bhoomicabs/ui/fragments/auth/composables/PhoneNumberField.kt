package com.terranullius.bhoomicabs.ui.fragments.auth.composables

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun PhoneNumberField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String,
    isError: MutableState<Boolean> = mutableStateOf(false),
    onDone: () -> Unit
) {
    TextField(
        modifier = modifier,
        value = value,
        label = {
            Text(text = "10 digit mobile number")
        },
        leadingIcon = {
            Text(text = "+91")
        },
        onValueChange = onValueChange,
        isError = isError.value,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            onDone()
        })
    )
}