package com.kroune.nineMensMorrisApp.di

import com.kroune.nineMensMorrisApp.data.remote.account.AccountInfoRepositoryI
import com.kroune.nineMensMorrisApp.data.remote.account.AccountInfoRepositoryImpl
import com.kroune.nineMensMorrisApp.data.remote.auth.AuthRepositoryI
import com.kroune.nineMensMorrisApp.data.remote.auth.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * application module
 * used for di
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    /**
     * auth repository
     * @see SignInScreen
     */
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepositoryI {
        return AuthRepositoryImpl()
    }

    /**
     * general account info
     * @see ViewAccountScreen
     */
    @Provides
    @Singleton
    fun provideAccountInfoRepository(): AccountInfoRepositoryI {
        return AccountInfoRepositoryImpl()
    }
}
