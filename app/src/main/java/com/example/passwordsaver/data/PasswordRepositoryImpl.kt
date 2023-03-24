package com.example.passwordsaver.data

import kotlinx.coroutines.flow.Flow

class PasswordRepositoryImpl(
    private val dao: PasswordDao
): PasswordRepository {

    override suspend fun insertPassword(password: Password) {
        dao.insertPassword(password)
    }

    override suspend fun deletePassword(password: Password) {
        dao.deletePassword(password)
    }

    override suspend fun getPasswordById(id: Int): Password {
        return dao.getPasswordById(id)
    }

    override fun getPasswords(): Flow<List<Password>> {
        return dao.getPasswords()
    }
}
