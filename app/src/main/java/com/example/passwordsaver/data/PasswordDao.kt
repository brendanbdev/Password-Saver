package com.example.passwordsaver.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
A DAO (Data Access Object) defines how to access data from the database.

The @Dao annotation is for Room, and the functions in this @Dao annotated
interface are queries for Room.
*/
@Dao
interface PasswordDao {

    /*
    (onConflict = OnConflictStrategy.REPLACE) replaces a password in the event
    that there is a request to insert a password with an ID that already exists.
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: Password)

    @Delete
    suspend fun deletePassword(password: Password)

    /*
    This custom query is requesting to select all properties from the password
    table where the id is equal to the id that is passed.
    */
    @Query("SELECT * FROM password WHERE id = :id")
    suspend fun getPasswordById(id: Int): Password

    /*
    This will be a Flow because I want this list to update as soon as it
    changes.

    This custom query is requesting to select everything from the password
    table.
    */
    @Query("SELECT * FROM password")
    fun getPasswords(): Flow<List<Password>>
}