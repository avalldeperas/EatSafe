package edu.uoc.avalldeperas.eatsafe.auth.login.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.auth.domain.use_cases.ValidateLoginInputUseCase
import edu.uoc.avalldeperas.eatsafe.auth.domain.model.LoginInputValidationType
import org.junit.Assert.assertEquals
import org.junit.Test


class ValidateLoginInputUseCaseTest {

    private val testee = ValidateLoginInputUseCase()

    @Test
    fun whenAllInputsAreCorrect_thenReturnsValidValidationType() {
        val result = testee.invoke("email@gmail.com", "123456")
        assertEquals(LoginInputValidationType.Valid, result)
    }

    @Test
    fun whenEmailNotSet_thenReturnsEmptyFieldValidationType() {
        val result = testee.invoke("", "password")
        assertEquals(LoginInputValidationType.EmptyField, result)
    }

    @Test
    fun whenPasswordNotSet_thenReturnsEmptyFieldValidationType() {
        val result = testee.invoke("email@gmail.com", "")
        assertEquals(LoginInputValidationType.EmptyField, result)
    }

    @Test
    fun whenEmailNotCorrect_thenReturnsInvalidEmailValidationType() {
        val result = testee.invoke("not_anEmail!23", "password")
        assertEquals(LoginInputValidationType.InvalidEmail, result)
    }
}