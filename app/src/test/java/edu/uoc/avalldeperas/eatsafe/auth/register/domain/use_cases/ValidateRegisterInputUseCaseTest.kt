package edu.uoc.avalldeperas.eatsafe.auth.register.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.auth.register.domain.model.RegisterInputValidationType
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidateRegisterInputUseCaseTest {
    private val validateRegisterInputUseCase = ValidateRegisterInputUseCase()

    @Test
    fun whenAllInputsAreCorrect_thenReturnsValidValidationType() {
        val result = validateRegisterInputUseCase.invoke(
            "email@gmail.com",
            "123456",
            "123456"
        )
        assertEquals(RegisterInputValidationType.Valid, result)
    }

    @Test
    fun whenEmailNotSet_thenReturnsEmptyFieldValidationType() {
        val result = validateRegisterInputUseCase.invoke("", "password", "")
        assertEquals(RegisterInputValidationType.EmptyField, result)
    }

    @Test
    fun whenEmailNotCorrect_thenReturnsInvalidEmailValidationType() {
        val result = validateRegisterInputUseCase.invoke(
            "not_anEmail!23",
            "123456",
            "123456"
        )
        assertEquals(RegisterInputValidationType.InvalidEmail, result)
    }

    @Test
    fun whenPasswordsDoNotMatch_thenReturnsPasswordDoNotMatchValidationType() {
        val result = validateRegisterInputUseCase.invoke(
            "email@gmail.com",
            "123456",
            "654321"
        )
        assertEquals(RegisterInputValidationType.PasswordsDoNotMatch, result)
    }

    @Test
    fun whenPasswordLengthLessThan6Chars_thenReturnsPasswordTooShortValidationType() {
        val result = validateRegisterInputUseCase.invoke(
            "email@gmail.com",
            "12345",
            "12345"
        )
        assertEquals(RegisterInputValidationType.PasswordTooShort, result)
    }
}