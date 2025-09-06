package com.doaa.mosalam.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.doaa.mosalam.data.local.favoriteBooks.FavoriteBookEntity
import com.doaa.mosalam.data.local.favoriteBooks.FavoriteDao
import com.doaa.mosalam.data.local.login.LoginDAO
import com.doaa.mosalam.data.local.login.LoginEntity
import com.doaa.mosalam.data.local.register.RegisterDAO
import com.doaa.mosalam.data.local.register.RegisterEntity


/**
 * The Room database for this app.
 */
@Database(
    entities = [
        LoginEntity::class,
        RegisterEntity::class,
        FavoriteBookEntity::class
    ],
    version = 1,
    exportSchema = false
)
    abstract class AppDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDAO

    abstract fun registerDao(): RegisterDAO
    abstract fun favoriteDao(): FavoriteDao
}

