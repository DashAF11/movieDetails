package com.example.moviedetails.di

import com.example.moviedetails.data.repository.GetMoviesRepository
import com.example.moviedetails.domain.IGetMovies
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepositoryModule(getMoviesRepository: GetMoviesRepository): IGetMovies
}