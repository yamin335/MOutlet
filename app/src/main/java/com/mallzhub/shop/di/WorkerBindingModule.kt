package com.mallzhub.shop.di

import com.mallzhub.shop.worker.DaggerWorkerFactory
import com.mallzhub.shop.worker.TokenRefreshWorker
import com.mallzhub.shop.worker.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class WorkerBindingModule {

    /*injector for DaggerWorkerFactory*/
    @Binds
    @IntoMap
    @WorkerKey(TokenRefreshWorker::class)
    abstract fun bindTokenRefreshWorker(factory: TokenRefreshWorker.Factory):
            DaggerWorkerFactory.ChildWorkerFactory


}
