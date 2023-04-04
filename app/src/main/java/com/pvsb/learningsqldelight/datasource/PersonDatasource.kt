package com.pvsb.learningsqldelight.datasource

import kotlinx.coroutines.flow.Flow
import learning.persondb.PersonEntity

interface PersonDatasource {

    suspend fun getPersonById(id: Long): PersonEntity?

    fun getAllPersons(): Flow<List<PersonEntity>>

    suspend fun deletePersonById(id: Long)

    suspend fun insertPerson(firstName: String, lastName: String, id: Long? = null)
}