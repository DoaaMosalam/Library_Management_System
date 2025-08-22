package com.doaa.mosalam.librarymanagementsystem.common.di

import android.app.Application
import androidx.room.Room
import com.doaa.mosalam.data.AppDatabase
import com.doaa.mosalam.librarymanagementsystem.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkRoomDataModule {

    @Provides
    @Singleton
    fun provideAppDatabaseBuilder(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            Constant.DATABASE_NAME


        ).addMigrations(
            AppDatabase.MIGRATION_1_2,
            AppDatabase.MIGRATION_2_3,
            AppDatabase.MIGRATION_3_4,
            AppDatabase.MIGRATION_4_5
        )
            .fallbackToDestructiveMigration() // This will destroy and recreate the database
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginDao(db: AppDatabase) = db.loginDao()

    @Provides
    @Singleton
    fun provideRegisterDao(db: AppDatabase) = db.registerDao()


}