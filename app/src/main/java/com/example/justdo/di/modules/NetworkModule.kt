package com.example.justdo.di.modules

import android.content.Context
import android.content.pm.ApplicationInfo
import com.example.justdo.App
import com.example.justdo.domain.entities.server.BaseServerInfo
import com.example.justdo.domain.entities.server.TokenInfo
import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.model.data.TodoMapCache
import com.example.justdo.model.data.server.CachedServerResponse
import com.example.justdo.model.data.server.ServerApi
import com.example.justdo.model.data.server.deserializer.TodoTasksListDeserializer
import com.example.justdo.model.data.server.deserializer.TokenInfoDeserializer
import com.example.justdo.model.data.server.error.ServerError
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.lang.Exception
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideServerApi(retrofit: Retrofit, todoMapCache: TodoMapCache): ServerApi =
        CachedServerResponse(retrofit.create(ServerApi::class.java), todoMapCache)

    @Provides
    @Singleton
    fun provideTodoMapCache() = TodoMapCache()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(App.SERVER_ADDRESS)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()


    @Suppress("UNUSED_PARAMETER")
    @Provides
    @Singleton
    fun provideOkHttpClient(
        context: Context,
        @Named("Logging Interceptor") loggingInterceptor: Interceptor,
        @Named("Error Handler Interceptor") errorHandlerInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient().newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(errorHandlerInterceptor)
            .cache(cache)
        //debug if not httpS
//            .hostnameVerifier { hostname, session ->
//                true
//            }
        val isDebug = ((context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0)
        if (isDebug) okHttpBuilder.addInterceptor(loggingInterceptor)

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    @Named("Logging Interceptor")
    fun provideLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.i(message) })
        httpLoggingInterceptor.level = (HttpLoggingInterceptor.Level.HEADERS)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    @Named("Error Handler Interceptor")
    fun provideErrorHandlerInterceptor() = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        val retResponse = originalResponse.newBuilder().build()

        val serverErrorMessage = "Server error\nTry again later"
        if (originalResponse.isSuccessful) {
            val jsonString = originalResponse.let {
                it.peekBody(Long.MAX_VALUE)?.run { string().also { close() } }
            }

            val info = try {
                GsonBuilder().create().fromJson(jsonString, BaseServerInfo::class.java)
            } catch (e: Exception) {
                throw ServerError(originalResponse.code(), serverErrorMessage)
            }

            if (info.success != true) {
                throw ServerError(
                    info.errorCode ?: HttpURLConnection.HTTP_INTERNAL_ERROR,
                    info.error ?: serverErrorMessage
                )
            }
        }
        else {
            throw ServerError(originalResponse.code(), serverErrorMessage)
        }
        retResponse
    }

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder()
        .setLenient()
        .excludeFieldsWithoutExposeAnnotation()
        .registerTypeAdapter(Array<TodoTask>::class.java, TodoTasksListDeserializer())
        .registerTypeAdapter(TokenInfo::class.java, TokenInfoDeserializer())
        .create()

    @Provides
    @Singleton
    fun provideCache(context: Context) = Cache(File(context.cacheDir, "http-cache"), 50 * MEGABYTE)

    companion object {
        private val BYTE = 1L
        private val KILOBYTE = 1024L * BYTE
        private val MEGABYTE = 1024L * KILOBYTE
    }
}