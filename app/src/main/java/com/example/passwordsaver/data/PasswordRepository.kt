package com.example.passwordsaver.data

import kotlinx.coroutines.flow.Flow

/*
A repository is for deciding exactly what data is passed to the view model. For
example, if you have multiple sources of data for one thing that is displayed
in the ui (like a remote API as one source of the data, and a local cache as
another source of the data), the repository implementation will have functions
to figure out exactly what data will be sent to the view model with respect to
the current conditions of the app in that point of time when the request is
made.

The repository is an interface because it will serve as an abstraction. In
other words, when you want to use the repository in the app, only these
functions can be used. This way, the repository can be easily replaced for
another repository or a test repository. This can be done using Hilt. In the
Hilt module in AppModule in the di package, the repository implementation
(PasswordRepositoryImpl) is provided, so all that needs to be done to use
the repository is injecting the repository into a constructor by making a
param be of type PasswordRepository, and then just using these functions
from that param.
*/
interface PasswordRepository {

    suspend fun insertPassword(password: Password)

    suspend fun deletePassword(password: Password)

    suspend fun getPasswordById(id: Int): Password?

    fun getPasswords(): Flow<List<Password>>
}
