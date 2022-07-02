package com.marturelo.themoviedbapp.di.module

import android.content.Context
import com.marturelo.themoviedbapp.BuildConfig
import com.marturelo.themoviedbapp.commons.utils.Constants
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {
/*    @Provides
    @Named(Constants.DI.HOST)
    fun provideTMDBHost(): String {
        return BuildConfig.HOST
    }

    @Provides
    @Named(Constants.DI.API_KEY)
    fun provideTMDBAPIKEY(): String {
        return BuildConfig.API_KEY
    }*/

    @Provides
    fun providesOkHttpClientBuilder(
        context: Context?,
    ): OkHttpClient.Builder? {
        val builder: OkHttpClient.Builder = Builder()
        builder.certificatePinner(
            Builder()
                .add(
                    BuildConfig.DOMAIN,
                    BuildConfig.PINNIG
                ).build()
        )
        builder.addInterceptor(requestHeaderInterceptor)
        builder.addInterceptor(responseStatusInterceptor)
        if (!BuildConfig.BUILD_TYPE.equals(Constants.RELEASE)) {
            builder.addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
        if (BuildConfig.IS_DEVELOPMENT || BuildConfig.BUILD_TYPE.equals(Constants.DEMO)) {
            builder.addInterceptor(
                OkHttpMockInterceptor(context, BuildConfig.ERROR_PROBABILITY)
            )
        }
        builder.readTimeout(Constants.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(Constants.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(Constants.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(false)
        return builder
    }
}