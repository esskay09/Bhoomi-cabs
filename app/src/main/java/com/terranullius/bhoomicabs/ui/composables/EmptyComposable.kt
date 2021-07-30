package com.terranullius.bhoomicabs.ui.composables

import android.animation.Animator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.terranullius.bhoomicabs.R

@Composable
fun EmptyComposable(modifier: Modifier = Modifier, onEnd : () -> Unit = {}) {

    AndroidView(
        modifier = modifier,
        factory = {
            LottieAnimationView(it).apply {
                setAnimation(R.raw.empty)
                repeatCount = 0

                this.addAnimatorListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(animation: Animator?, isReverse: Boolean) = Unit

                    override fun onAnimationStart(animation: Animator?)= Unit

                    override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                        onEnd()
                    }


                    override fun onAnimationEnd(animation: Animator?) {
                        onEnd()
                    }

                    override fun onAnimationCancel(animation: Animator?)= Unit

                    override fun onAnimationRepeat(animation: Animator?) = Unit
                })
            }
        }
    ) {
        it.playAnimation()
    }

}