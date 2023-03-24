package com.example.passwordsaver.data

import androidx.room.Database
import androidx.room.RoomDatabase

/*
This @Database annotation and it's arguments are telling Room the entities
(tables), version (which should be updated when the database changes) and
*/
@Database(
    entities = [Password::class],
    version = 1
)
abstract class PasswordDatabase: RoomDatabase() {

    abstract val dao: PasswordDao
}
