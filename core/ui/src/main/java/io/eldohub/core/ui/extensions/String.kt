package io.eldohub.core.ui.extensions

import android.text.TextUtils
import android.util.Patterns
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kotlin.text.matches

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.matchesPasswordRegex(): Boolean {
    val regex = Constants.PASSWORD_VALIDATION_REGEX.toRegex()
    return regex.matches(this)
}

fun String.buildClickableText(
    clickableText: String,
    style: SpanStyle,
    tag: String,
    onClick: ((link: LinkAnnotation) -> Unit)? = null
): AnnotatedString {
    return buildAnnotatedString {
        val startIndex = this@buildClickableText.indexOf(clickableText)
        val endIndex = startIndex + clickableText.length

        withStyle(style = style) { append(this@buildClickableText) }
        addLink(
            LinkAnnotation.Clickable(
                tag = tag,
                linkInteractionListener = onClick
            ),
            startIndex,
            endIndex
        )
    }
}