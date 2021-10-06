package com.mallzhub.shop.di

import com.mallzhub.shop.ui.LoginActivity
import com.mallzhub.shop.ui.MainActivity
import com.mallzhub.shop.ui.SplashActivity
import com.mallzhub.shop.ui.live_chat.LiveChatActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeSplashActivity(): SplashActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeLoginActivity(): LoginActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeLiveChatActivity(): LiveChatActivity

}