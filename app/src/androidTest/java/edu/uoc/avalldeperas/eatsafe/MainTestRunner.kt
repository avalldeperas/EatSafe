package edu.uoc.avalldeperas.eatsafe

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication
import edu.uoc.avalldeperas.eatsafe.e2e.features.auth.EatSafeE2ETests
import edu.uoc.avalldeperas.eatsafe.ui.auth.forgot_password.presentation.ForgotPasswordScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.auth.login.presentation.LoginScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.auth.register.presentation.RegisterScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.explore.DetailViewScreenTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    // UI Tests
    ForgotPasswordScreenTest::class,
    LoginScreenTest::class,
    RegisterScreenTest::class,
    DetailViewScreenTest::class,
    // E2E tests
    EatSafeE2ETests::class
)
class MainTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}