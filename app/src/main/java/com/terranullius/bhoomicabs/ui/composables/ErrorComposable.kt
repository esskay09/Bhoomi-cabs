package com.terranullius.bhoomicabs.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.terranullius.bhoomicabs.R

@Composable
fun ErrorComposable(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        AndroidView(
            factory = {
                LottieAnimationView(it).apply {
                    setAnimation(R.raw.error)
                    repeatCount = LottieDrawable.INFINITE
                    repeatMode = LottieDrawable.RESTART
                }
            }
        ) {
            it.playAnimation()
        }
        Text(text = "Something went wrong", Modifier.align(Alignment.CenterHorizontally))
    }

}