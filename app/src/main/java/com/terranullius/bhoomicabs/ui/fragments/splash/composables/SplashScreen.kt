package com.terranullius.bhoomicabs.ui.fragments.splash.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.terranullius.bhoomicabs.R

@ExperimentalAnimationApi
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    var logoVisible by remember{
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit){
        logoVisible = true
    }

    AnimatedVisibility(visible = logoVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 2000, delayMillis = 150))
                + expandIn(animationSpec = tween(durationMillis = 2000, delayMillis = 150)))
    {
        Box(modifier = modifier, contentAlignment = Alignment.Center){
            Image(
                modifier = Modifier.fillMaxWidth(0.8f),
                painter = painterResource(id = R.drawable.bhoomi_travels_logo),
                contentScale = ContentScale.FillWidth,
                contentDescription = "logo"
            )
        }
    }
}