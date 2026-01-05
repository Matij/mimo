package fm.mimo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * To enable unit testing of suspend functions, use this provider.
 * Ignore the dispatcher IDE warnings only in this file.
 */
interface DispatcherProvider {
    fun main(): CoroutineDispatcher = Dispatchers.Main

    fun default(): CoroutineDispatcher = Dispatchers.Default

    fun io(): CoroutineDispatcher

    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined

    fun onIo(coroutineScope: CoroutineScope) = coroutineScope.onDispatcher(io())
    fun onIo(coroutineContext: CoroutineContext) = coroutineContext.onDispatcher(io())
    fun onMain(coroutineScope: CoroutineScope) = coroutineScope.onDispatcher(main())
    fun onMain(coroutineContext: CoroutineContext) = coroutineContext.onDispatcher(main())
}

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

    private val unboundedThreadPool = Executors.newCachedThreadPool().asCoroutineDispatcher()

    override fun io() = unboundedThreadPool
}

/** For use with contexts that contain jobs, e.g. viewModelScope.onDispatcher(dispatcher) will create a context
 * that will run the given dispatcher but terminate when viewModelScope is cancelled
 */
fun CoroutineScope.onDispatcher(dispatcher: CoroutineDispatcher) =
    this.coroutineContext + dispatcher

fun CoroutineContext.onDispatcher(dispatcher: CoroutineDispatcher) = this + dispatcher
