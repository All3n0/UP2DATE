package io.eldohub.core.ui.extensions

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope

val LocalFixedInsets = compositionLocalOf<FixedInsets> {
    error("no FixedInsets provided!")
}

val LocalCoroutineScope = staticCompositionLocalOf<CoroutineScope> {
    error("No coroutine scope provided!")
}