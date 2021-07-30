package com.terranullius.bhoomicabs.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.terranullius.bhoomicabs.R

@Composable
fun LoadingComposable(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            LottieAnimationView(it).apply {
                setAnimation(R.raw.loading)
                repeatCount = LottieDrawable.INFINITE
                repeatMode = LottieDrawable.RESTART
            }
        }
    ) {
        it.playAnimation()
    }
}