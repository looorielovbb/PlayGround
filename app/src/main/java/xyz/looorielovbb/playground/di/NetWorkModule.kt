package xyz.looorielovbb.playground.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.looorielovbb.playground.data.remote.WanApiService
import xyz.looorielovbb.playground.utils.MoshiEx.moshi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Provides
    @Singleton
    fun bindClient(): OkHttpClient =
        OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                setLevel(HttpLoggingInterceptor.Level.BASIC)
//            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, converter: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converter)
            .baseUrl(WanApiService.BASE_URL)
            .build()

    @Provides
    @Singleton
    fun bindConverter(): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun bindCreate(retrofit: Retrofit): WanApiService =
        retrofit.create(WanApiService::class.java)

}