package com.example.passwordsaver.di

import android.app.Application
import androidx.room.Room
import com.example.passwordsaver.data.PasswordDatabase
import com.example.passwordsaver.data.PasswordRepository
import com.example.passwordsaver.data.PasswordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Hilt module for defining dependencies and their lifetime. The module its
self has a lifetime of the application.
*/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // TODO: REMOVE .fallbackToDestructiveMigration() AND POSSIBLY USE MIGRATION.
    @Provides
    @Singleton
    fun providePasswordDatabase(app: Application): PasswordDatabase {
        return Room.databaseBuilder(
            app,
            PasswordDatabase::class.java,
            "password_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePasswordRepository(db: PasswordDatabase): PasswordRepository {
        return PasswordRepositoryImpl(db.dao)
    }
}
