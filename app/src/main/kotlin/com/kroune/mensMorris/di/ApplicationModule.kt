package com.kroune.mensMorris.di

import com.kroune.mensMorris.data.remote.AuthRepositoryI
import com.kroune.mensMorris.data.remote.AuthRepositoryImpl
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
     * @see OnlineGameScreen
     */
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepositoryI {
        return AuthRepositoryImpl()
    }
}
