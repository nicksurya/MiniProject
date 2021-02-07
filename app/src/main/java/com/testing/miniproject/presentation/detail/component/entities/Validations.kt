package com.testing.miniproject.presentation.detail.component.entities

import java.util.regex.Pattern

class Validations(val rule: Rule, val value: String, val errorMessage: String) {

    fun isInputValid(dataValue: String): Boolean {
        return when (rule) {
            Rule.REQUIRED -> dataValue.isNotBlank()
            Rule.MIN_LENGTH -> dataValue.length >= Integer.parseInt(value)
            Rule.MAX_LENGTH -> dataValue.length <= Integer.parseInt(value)
            Rule.MIN_VALUE -> dataValue.toDouble() >= value.toDouble()
            Rule.MAX_VALUE -> dataValue.toDouble() <= value.toDouble()
            else -> Pattern.compile(value, Pattern.UNICODE_CASE).matcher(dataValue).matches()
        }
    }
}