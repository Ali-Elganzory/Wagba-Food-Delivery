package com.ganzory.wagba.shared

import android.widget.EditText

class AuthValidator(
    private val tfEmail: EditText,
    private val tfPassword: EditText,
    private val emailPattern: String = "^[a-zA-Z0-9._-]+@[a-z]+(\\.[a-z]+)+$",
    private val validEmailOrg: String,
    private val validEmailPattern: String,
    private val validPassShort: String,
) {
    fun validate(): Triple<Boolean, String, String> {
        val email = tfEmail.text.toString().trim()
        val password = tfPassword.text.toString().trim()
        // Email
        var emailValid = false
        if (!email.matches(".*@eng.asu.edu.eg$".toRegex())) {
            tfEmail.error = validEmailOrg
        } else if (email.isEmpty() || !email.matches(emailPattern.toRegex())) {
            tfEmail.error = validEmailPattern
        } else {
            emailValid = true
        }
        // Password
        var passwordValid = false
        if (password.length < 6) {
            tfPassword.error = validPassShort
        } else {
            passwordValid = true
        }
        return Triple((emailValid && passwordValid), email, password)
    }
}