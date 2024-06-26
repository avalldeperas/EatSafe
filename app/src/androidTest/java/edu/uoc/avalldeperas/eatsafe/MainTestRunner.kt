package edu.uoc.avalldeperas.eatsafe

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication
import edu.uoc.avalldeperas.eatsafe.e2e.features.auth.AuthTests
import edu.uoc.avalldeperas.eatsafe.e2e.features.explore.ExploreJourney
import edu.uoc.avalldeperas.eatsafe.e2e.features.favorite.FavoritesJourney
import edu.uoc.avalldeperas.eatsafe.e2e.features.profile.EditProfileJourney
import edu.uoc.avalldeperas.eatsafe.e2e.features.review.ReviewsJourney
import edu.uoc.avalldeperas.eatsafe.ui.auth.ForgotPasswordScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.auth.LoginScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.auth.RegisterScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.explore.DetailViewScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.favorites.FavoritesScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.profile.EditProfileScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.profile.ProfileScreenTest
import edu.uoc.avalldeperas.eatsafe.ui.reviews.AddReviewScreenTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    // UI Tests
    ForgotPasswordScreenTest::class,
    LoginScreenTest::class,
    RegisterScreenTest::class,
    DetailViewScreenTest::class,
    AddReviewScreenTest::class,
    FavoritesScreenTest::class,
    ProfileScreenTest::class,
    EditProfileScreenTest::class,
    // E2E tests
    AuthTests::class,
    FavoritesJourney::class,
    ReviewsJourney::class,
    EditProfileJourney::class,
    ExploreJourney::class
)
class MainTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}