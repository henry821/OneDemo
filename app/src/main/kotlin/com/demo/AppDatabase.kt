package com.demo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.modules.paging.User
import com.demo.modules.paging.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

object DatabaseHolder {

    lateinit var db: AppDatabase

    fun init(context: Context) {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "one_demo")
            .createFromAsset("database/one_demo.db")
            .build()
    }
}