package com.muhammad.domain.usecase

import androidx.core.util.PatternsCompat

class ValidateUserName {

    fun execute(userName : String) : ValidationResult {
        if(userName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Username is empty."
            )
        }
        if(userName.length<3) {
            return ValidationResult(
                successful = false,
                errorMessage = "Username is not valid."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}