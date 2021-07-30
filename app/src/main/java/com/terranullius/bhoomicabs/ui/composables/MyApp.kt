package com.terranullius.bhoomicabs.ui.composables

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.terranullius.bhoomicabs.ui.composables.theme.BhoomicabsTheme
import com.terranullius.bhoomicabs.ui.composables.theme.selectCarBackgroundColor

@Composable
fun MyApp(content: @Composable () -> Unit) {
    BhoomicabsTheme() {
        Surface(color = selectCarBackgroundColor) {
            content()
        }
    }
}