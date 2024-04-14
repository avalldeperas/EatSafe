package edu.uoc.avalldeperas.eatsafe.auth.login.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.LoginInputValidationType
import org.junit.Assert.assertEquals
import org.junit.Test


class ValidateLoginInputUseCaseTest {

    private val validateLoginInputUseCase = ValidateLoginInputUseCase()

    @Test
    fun whenAllInputsAreCorrect_thenReturnsValidValidationType() {
        val result = validateLoginInputUseCase.invoke("email@gmail.com", "123456")
        assertEquals(LoginInputValidationType.Valid, result)
    }

    @Test
    fun whenEmailNotSet_thenReturnsEmptyFieldValidationType() {
        val result = validateLoginInputUseCase.invoke("", "password")
        assertEquals(LoginInputValidationType.EmptyField, result)
    }

    @Test
    fun whenPasswordNotSet_thenReturnsEmptyFieldValidationType() {
        val result = validateLoginInputUseCase.invoke("email@gmail.com", "")
        assertEquals(LoginInputValidationType.EmptyField, result)
    }

    @Test
    fun whenEmailNotCorrect_thenReturnsInvalidEmailValidationType() {
        val result = validateLoginInputUseCase.invoke("not_anEmail!23", "password")
        assertEquals(LoginInputValidationType.InvalidEmail, result)
    }
}