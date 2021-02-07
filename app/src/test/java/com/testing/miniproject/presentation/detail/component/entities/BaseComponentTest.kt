package com.testing.miniproject.presentation.detail.component.entities

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class BaseComponentTest {

    lateinit var componentName : BaseComponent

    @Before
    fun setUp() {
        componentName = BaseComponent("name", "Name", arrayListOf(
            Validations(Rule.REQUIRED, "", "Harap Isi Nama"),
            Validations(Rule.MIN_LENGTH, "4", "Nama Minimum 4 Karakter"),
            Validations(Rule.MAX_LENGTH, "40", "Nama Maksimum 40 Karakter"),
        ), false)
    }

    @Test
    fun givenValue_WillReturnEmptyErrorMessage() {
        //given
        val value = "Alexandria"

        //when
        val errorMessage = componentName.isValid(value)

        //then
        assertTrue(errorMessage.isNullOrEmpty())
    }

    @Test
    fun givenEmptyValue_WillReturnSpecificErrorMessage() {
        //given
        val value = ""

        //when
        val errorMessage = componentName.isValid(value)

        //then
        assertFalse(errorMessage.isNullOrEmpty())
        assertEquals(errorMessage, "Harap Isi Nama")
    }

    @Test
    fun givenInvalidLengthValue_WillReturnSpecificErrorMessage() {
        //given
        val value = "Ani"

        //when
        val errorMessage = componentName.isValid(value)

        //then
        assertFalse(errorMessage.isNullOrEmpty())
        assertEquals(errorMessage, "Nama Minimum 4 Karakter")
    }
}