package com.georgiopoulos.core.data.di

import com.georgiopoulos.core.data.AppDispatchers
import com.georgiopoulos.core.data.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Dagger module providing the dispatchers (execution contexts) used in the application.
 * This module is installed in the SingletonComponent, indicating that the provided dependencies are available as singletons.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {

    /**
     * Provides the IO dispatcher as a CoroutineDispatcher for executing input/output operations or other blocking tasks.
     *
     * @return the IO dispatcher as a CoroutineDispatcher
     */
    @Provides
    @Dispatcher(AppDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
