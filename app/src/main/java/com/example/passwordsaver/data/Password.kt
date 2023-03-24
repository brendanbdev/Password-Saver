package com.example.passwordsaver.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
This is an "entity". It contains all of the fields in an item (in this case, a
password).

The @Entity annotation tells Room that this is an entity, which will actually
make this a table in our database.
*/
@Entity
data class Password(
    val title: String,
    val passwordValue: String,
    val isVisible: Boolean,
    /*
    The @PrimaryKey annotation tells Room that you want this field to be the
    primary key. It is nullable and null because I want Room to generate the
    value, and Room will do that if the value is initially null.
    */
    @PrimaryKey val id: Int? = null
)
