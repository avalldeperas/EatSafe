package edu.uoc.avalldeperas.eatsafe.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepositoryImpl
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepositoryImpl
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepositoryImpl
import edu.uoc.avalldeperas.eatsafe.favorites.data.FavoritesRepository
import edu.uoc.avalldeperas.eatsafe.favorites.data.FavoritesRepositoryImpl
import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepository
import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository
    @Binds abstract fun provideUsersRepository(impl: UsersRepositoryImpl): UsersRepository

    @Binds abstract fun providePlacesRepository(impl: PlaceRepositoryImpl): PlaceRepository

    @Binds abstract fun provideReviewRepository(impl: ReviewsRepositoryImpl): ReviewsRepository

    @Binds abstract fun provideFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository


//    @Binds abstract fun provideDummyPlacesRepository(impl: PlaceRepositoryDummyImpl): PlaceRepository
}