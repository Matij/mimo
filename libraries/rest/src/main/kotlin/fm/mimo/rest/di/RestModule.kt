package fm.mimo.rest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import fm.mimo.di_android.ApiBaseUrl
import fm.mimo.di_android.BuildConfigDebug
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class OkHttpInterceptor

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class OkHttpNetworkInterceptor

@Module
@InstallIn(SingletonComponent::class)
object RestModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @OkHttpInterceptor interceptors: Set<@JvmSuppressWildcards Interceptor>,
        @OkHttpNetworkInterceptor networkInterceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .retryOnConnectionFailure(false)
            .apply {
                interceptors
                    .forEach {
                        addInterceptor(it)
                    }
                networkInterceptors
                    .forEach {
                        addNetworkInterceptor(it)
                    }
            }
            .build()

    @Provides
    @IntoSet
    @OkHttpNetworkInterceptor
    fun provideLoggingInterceptor(
        @BuildConfigDebug debugBuild: Boolean,
    ): Interceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (debugBuild) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    fun provideRetrofit(
        @ApiBaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class Bindings {
        // The declaration of multi-binding is necessary in case
        // there is no bindings and set is empty.
        @Multibinds
        @OkHttpInterceptor
        abstract fun bindInterceptorsSet(): Set<Interceptor>

        @Multibinds
        @OkHttpNetworkInterceptor
        abstract fun bindNetworkInterceptorsSet(): Set<Interceptor>
    }
}
