package edu.uoc.avalldeperas.eatsafe.auth.register.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.auth.register.domain.model.RegisterInputValidationType
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidateRegisterInputUseCaseTest {

    private val testee = ValidateRegisterInputUseCase()

    @Test
    fun whenAllInputsAreCorrect_thenReturnsValidValidationType() {
        val result = testee.invoke(
            "email@gmail.com",
            "123456",
            "123456",
            "Barcelona"
        )
        assertEquals(RegisterInputValidationType.Valid, result)
    }

    @Test
    fun whenEmailNotSet_thenReturnsEmptyFieldValidationType() {
        val result = testee.invoke("", "password", "", "")
        assertEquals(RegisterInputValidationType.EmptyField, result)
    }

    @Test
    fun whenEmailNotCorrect_thenReturnsInvalidEmailValidationType() {
        val result = testee.invoke(
            "not_anEmail!23",
            "123456",
            "123456",
            "Barcelona"
        )
        assertEquals(RegisterInputValidationType.InvalidEmail, result)
    }

    @Test
    fun whenPasswordsDoNotMatch_thenReturnsPasswordDoNotMatchValidationType() {
        val result = testee.invoke(
            "email@gmail.com",
            "123456",
            "654321",
            "Barcelona"
        )
        assertEquals(RegisterInputValidationType.PasswordsDoNotMatch, result)
    }

    @Test
    fun whenPasswordLengthLessThan6Chars_thenReturnsPasswordTooShortValidationType() {
        val result = testee.invoke(
            "email@gmail.com",
            "12345",
            "12345",
            "Barcelona"
        )
        assertEquals(RegisterInputValidationType.PasswordTooShort, result)
    }

    @Test
    fun whenAddressLengthLessThan6Chars_thenReturnsAddressTooShortValidationType() {
        val result = testee.invoke(
            "email@gmail.com",
            "123456",
            "123456",
            "asdf"
        )
        assertEquals(RegisterInputValidationType.AddressTooShort, result)
    }

    @Test
    fun whenAddressContainsInvalidChars_thenReturnsAddressInvalidValidationType() {
        val result = testee.invoke(
            "email@gmail.com",
            "123456",
            "123456",
            "asdf%%"
        )
        assertEquals(RegisterInputValidationType.InvalidAddress, result)
    }
}