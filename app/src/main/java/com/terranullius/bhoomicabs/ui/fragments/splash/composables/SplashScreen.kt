package com.terranullius.bhoomicabs.ui.fragments.splash.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.terranullius.bhoomicabs.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.bhoomi_travels_logo), contentDescription = "logo")
    }
}