package com.testing.miniproject.presentation.detail.component.entities

open class BaseComponent(
    val id : String,
    val title : String,
    val validations : List<Validations>,
    val enableChain : Boolean
) {
    fun isValid(value : String) : String? {
        for (validation in validations) {
            if (!validation.isInputValid(value)) {
                return validation.errorMessage
            }
        }
        return null
    }
}