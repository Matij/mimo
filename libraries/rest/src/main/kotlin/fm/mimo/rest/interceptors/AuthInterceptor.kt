package fm.mimo.rest.interceptors

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import fm.mimo.rest.di.OkHttpInterceptor
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        return chain.proceed(request.build())
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindAuthInterceptor {
    @Binds
    @IntoSet
    @OkHttpInterceptor
    abstract fun bindAuthInterceptor(bind: AuthInterceptor): Interceptor
}
