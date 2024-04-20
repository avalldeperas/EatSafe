package edu.uoc.avalldeperas.eatsafe.profile.details.domain.use_cases

import org.junit.Assert
import org.junit.Test

class EditProfileInputValidationTypeTest {

    private val testee = ValidateEditProfileInputUseCase()

    @Test
    fun whenAllInputsAreCorrect_thenReturnsValidValidationType() {
        val result = testee.invoke("John Doe", "Barcelona")
        Assert.assertEquals(EditProfileInputValidationType.Valid, result)
    }

    @Test
    fun whenDisplayNameTooShort_thenReturnsDisplayNameTooShortValidationType() {
        val result = testee.invoke("A", "Barcelona")
        Assert.assertEquals(EditProfileInputValidationType.DisplayNameTooShort, result)
    }

    @Test
    fun whenAddressTooShort_thenReturnsAddressTooShortValidationType() {
        val result = testee.invoke("John Doe", "Ba")
        Assert.assertEquals(EditProfileInputValidationType.AddressTooShort, result)
    }

    @Test
    fun whenAddressHasSpecialCharacters_thenReturnsInvalidAddressValidationType() {
        val result = testee.invoke("John Doe", "Ba%dasdf")
        Assert.assertEquals(EditProfileInputValidationType.InvalidAddress, result)
    }
}