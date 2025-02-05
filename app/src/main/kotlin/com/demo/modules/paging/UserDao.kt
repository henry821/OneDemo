package com.demo.modules.paging

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id > :lastId LIMIT :count")
    suspend fun loadNextPageUsers(lastId: Int, count: Int): List<User>

    @Query("SELECT * FROM user WHERE id <= :lastId LIMIT :count")
    suspend fun loadPrevPageUsers(lastId: Int, count: Int): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg users: User)

    @Delete
    suspend fun delete(user: User)

}