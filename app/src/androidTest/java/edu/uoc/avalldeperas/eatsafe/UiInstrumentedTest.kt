package edu.uoc.avalldeperas.eatsafe

import edu.uoc.avalldeperas.eatsafe.auth.forgot_password.presentation.ForgotPasswordScreenTest
import edu.uoc.avalldeperas.eatsafe.auth.login.presentation.LoginScreenTest
import edu.uoc.avalldeperas.eatsafe.auth.register.presentation.RegisterScreenTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    ForgotPasswordScreenTest::class,
    LoginScreenTest::class,
    RegisterScreenTest::class
)
class UiInstrumentedTest