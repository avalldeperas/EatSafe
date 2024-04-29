package edu.uoc.avalldeperas.eatsafe.e2e.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.di.AppModule
import edu.uoc.avalldeperas.eatsafe.e2e.data.repository.FakeAuthRepository
import edu.uoc.avalldeperas.eatsafe.e2e.data.repository.FakeFavoriteRepository
import edu.uoc.avalldeperas.eatsafe.e2e.data.repository.FakePlaceRepository
import edu.uoc.avalldeperas.eatsafe.e2e.data.repository.FakeReviewsRepository
import edu.uoc.avalldeperas.eatsafe.e2e.data.repository.FakeUserRepository
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.favorites.data.FavoritesRepository
import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
abstract class TestAppModule {

    @Singleton
    @Binds
    abstract fun provideFakeAuthRepository(impl: FakeAuthRepository): AuthRepository
    @Singleton
    @Binds
    abstract fun provideFakeUsersRepository(impl: FakeUserRepository): UsersRepository
    @Binds
    @Singleton
    abstract fun provideFakeReviewsRepository(impl: FakeReviewsRepository): ReviewsRepository
    @Binds
    @Singleton
    abstract fun provideFakePlacesRepository(impl: FakePlaceRepository): PlaceRepository
    @Binds
    @Singleton
    abstract fun provideFakeFavoritesRepository(impl: FakeFavoriteRepository): FavoritesRepository
}