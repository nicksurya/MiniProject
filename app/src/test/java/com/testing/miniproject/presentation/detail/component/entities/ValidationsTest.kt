package com.testing.miniproject.presentation.detail.component.entities

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ValidationsTest {

    lateinit var validationRequired : Validations
    lateinit var validationMinLength : Validations
    lateinit var validationMaxLength : Validations
    lateinit var validationMinValue : Validations
    lateinit var validationMaxValue : Validations
    lateinit var validationRegex : Validations

    @Before
    fun setUp() {
        validationRequired = Validations(Rule.REQUIRED, "", "Harap Diisi")
        validationMinLength = Validations(Rule.MIN_LENGTH, "3", "Harap Diisi")
        validationMaxLength = Validations(Rule.MAX_LENGTH, "10", "Harap Diisi")
        validationMinValue = Validations(Rule.MIN_VALUE, "1000", "Harap Diisi")
        validationMaxValue = Validations(Rule.MAX_VALUE, "10000", "Harap Diisi")
        validationRegex = Validations(Rule.REGEX, "^([8]{1})([0-9]{10,13})\$", "Harap Diisi") //phone number start with 8 and length between 10-13

    }

    @Test
    fun givenValue_onRequiredRule_isInputValid() {
        //given
        val emptyValue = ""
        val anyValue = "Apaaja"

        //then
        assertFalse(validationRequired.isInputValid(emptyValue))
        assertTrue(validationRequired.isInputValid(anyValue))
    }

    @Test
    fun givenValue_onMinLengthRule_isInputValid() {
        //given
        val twoChars = "AB"
        val manyChars = "Apaaja"

        //then
        assertFalse(validationMinLength.isInputValid(twoChars))
        assertTrue(validationMinLength.isInputValid(manyChars))
    }

    @Test
    fun givenValue_onMaxLengthRule_isInputValid() {
        //given
        val shortChars = "AB"
        val manyChars = "ApaAjaPokoknyaPanjang"

        //then
        assertFalse(validationMaxLength.isInputValid(manyChars))
        assertTrue(validationMaxLength.isInputValid(shortChars))
    }

    @Test
    fun givenValue_onMinValueRule_isInputValid() {
        //given
        val smallAmount = "10"
        val bigAmount = "10000000"

        //then
        assertFalse(validationMinValue.isInputValid(smallAmount))
        assertTrue(validationMinValue.isInputValid(bigAmount))
    }

    @Test
    fun givenValue_onMaxValueRule_isInputValid() {
        //given
        val smallAmount = "10"
        val bigAmount = "10000000"

        //then
        assertFalse(validationMaxValue.isInputValid(bigAmount))
        assertTrue(validationMaxValue.isInputValid(smallAmount))
    }

    @Test
    fun givenValue_onRegex_isInputValid() {
        //given
        val validPhone = "81919991999"
        val invalidPhone = "081919991999"

        //then
        assertTrue(validationRegex.isInputValid(validPhone))
        assertFalse(validationRegex.isInputValid(invalidPhone))
    }
}