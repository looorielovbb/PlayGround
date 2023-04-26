package xyz.looorielovbb.playground.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.looorielovbb.playground.data.remote.WanApi
import xyz.looorielovbb.playground.ext.MoshiEx.moshi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Provides
    @Singleton
    fun bindClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverter: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .baseUrl(WanApi.BASE_URL)
            .build()

    @Provides
    @Singleton
    fun bindConverter(): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun bindCreate(retrofit: Retrofit): WanApi =
        retrofit.create(WanApi::class.java)

}