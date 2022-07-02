package com.marturelo.themoviedbapp.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.marturelo.themoviedbapp.BuildConfig
import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.commons.utils.Constants.API.API_CONNECTION_TIMEOUT
import com.marturelo.themoviedbapp.commons.utils.Constants.API.ISO_DATE_FORMAT
import com.marturelo.themoviedbapp.data.api.TMDBApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import java.util.concurrent.TimeUnit
import javax.inject.Named
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @Named(Constants.DI.API_KEY)
    fun provideTMDBAPIKEY(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    fun providesOkHttpClientBuilder(
    ): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
        builder.readTimeout(API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(false)
        return builder.build()
    }

    @Reusable
    @Provides
    fun providesGson(): Gson {
        val builder = GsonBuilder()
        builder.setDateFormat(ISO_DATE_FORMAT)
        return builder.create()
    }

    @Reusable
    @Provides
    fun providesRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(BuildConfig.HOST)
        builder.client(client)
        builder.addConverterFactory(GsonConverterFactory.create(gson))
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return builder.build()
    }

    @Provides
    @Reusable
    fun provideTMDBApi(retrofit: Retrofit): TMDBApi {
        return retrofit.create(TMDBApi::class.java)
    }
}