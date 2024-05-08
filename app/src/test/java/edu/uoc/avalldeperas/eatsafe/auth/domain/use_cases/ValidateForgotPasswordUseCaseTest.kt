package edu.uoc.avalldeperas.eatsafe.auth.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.auth.domain.model.ForgotPasswordInputValidationType
import org.junit.Assert
import org.junit.Test

class ValidateForgotPasswordUseCaseTest {

    private val testee = ValidateForgotPasswordUseCase()

    @Test
    fun whenAllInputsAreCorrect_thenReturnsValidValidationType() {
        val result = testee.invoke("email@gmail.com")
        Assert.assertEquals(ForgotPasswordInputValidationType.Valid, result)
    }

    @Test
    fun whenEmailNotSet_thenReturnsEmptyFieldValidationType() {
        val result = testee.invoke("")
        Assert.assertEquals(ForgotPasswordInputValidationType.EmptyField, result)
    }

    @Test
    fun whenEmailNotCorrect_thenReturnsInvalidEmailValidationType() {
        val result = testee.invoke("not_anEmail!23")
        Assert.assertEquals(ForgotPasswordInputValidationType.InvalidEmail, result)
    }
}