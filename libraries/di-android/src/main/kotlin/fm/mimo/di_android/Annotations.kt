package fm.mimo.di_android

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BuildConfigDebug

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InterceptorsEnabled

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BuildFlavorMock

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BuildFlavor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainCoroutineScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoCoroutineScope

