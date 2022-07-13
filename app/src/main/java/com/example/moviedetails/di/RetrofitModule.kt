package com.example.moviedetails.di

import com.example.moviedetails.data.apiService.ApiInterface
import com.example.moviedetails.data.apiService.APIConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .client(OkHttpClient().newBuilder().apply {
                callTimeout(40, TimeUnit.SECONDS)
                connectTimeout(40, TimeUnit.SECONDS)
                readTimeout(40, TimeUnit.SECONDS)
                writeTimeout(40, TimeUnit.SECONDS)
                addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                )
                retryOnConnectionFailure(true)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideMovieApiServices(retrofit: Retrofit.Builder): ApiInterface =
        retrofit.baseUrl(APIConstant.BASE_URL)
            .build().create((ApiInterface::class.java))
}