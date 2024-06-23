package com.kroune.mensMorris.di

import com.kroune.mensMorris.data.remote.account.AccountInfoRepositoryI
import com.kroune.mensMorris.data.remote.account.AccountInfoRepositoryImpl
import com.kroune.mensMorris.data.remote.auth.AuthRepositoryI
import com.kroune.mensMorris.data.remote.auth.AuthRepositoryImpl
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
