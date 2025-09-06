package io.eldohub.core.ui.extensions

object Constants {
    const val PASSWORD_VALIDATION_REGEX = """.*(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}.*"""
    const val EMAIL_VALIDATION_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
    const val GUEST_ID = "GUEST_ID_KEY"
    const val IS_AUTHENTICATED = "IS_AUTHENTICATED_KEY"
    const val USER_EMAIL = "USER_EMAIL_KEY";
    const val USER_PASSWORD = "USER_PASSWORD_KEY"
}