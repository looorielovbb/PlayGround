package xyz.looorielovbb.playground.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import xyz.looorielovbb.playground.data.remote.WanApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(WanApi.BASE_URL)
            .build()

    @Provides
    @Singleton
    fun bindCreate(retrofit: Retrofit): WanApi {
        return retrofit.create(WanApi::class.java)
    }

}