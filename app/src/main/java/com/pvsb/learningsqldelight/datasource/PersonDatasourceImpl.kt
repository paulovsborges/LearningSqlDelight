package com.pvsb.learningsqldelight.datasource

import com.pvsb.learningsqldelight.PersonDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import learning.persondb.PersonEntity

class PersonDatasourceImpl(
    db: PersonDatabase
) : PersonDatasource {

    private val queries = db.personEntityQueries

    override suspend fun getPersonById(id: Long): PersonEntity? {
        return queries.getPersonById(id).executeAsOneOrNull()
    }

    override fun getAllPersons(): Flow<List<PersonEntity>> {
        return queries.getAllPersons().asFlow().mapToList()
    }

    override suspend fun deletePersonById(id: Long) {
        queries.deletePersonById(id)
    }

    override suspend fun insertPerson(firstName: String, lastName: String, id: Long?) {
        queries.insertPerson(id, firstName, lastName)
    }
}