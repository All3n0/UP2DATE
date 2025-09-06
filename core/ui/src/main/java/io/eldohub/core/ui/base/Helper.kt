package io.eldohub.core.ui.base

import java.util.UUID

object Helper {
    fun generateUuid(): String {
        return UUID.randomUUID().toString().lowercase()
    }

    fun joinWithColon(first: String, second: String): String {
        return "$first:$second"
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // regular expressions preferred
    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}