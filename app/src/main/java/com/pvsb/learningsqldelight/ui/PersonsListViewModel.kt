package com.pvsb.learningsqldelight.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.learningsqldelight.datasource.PersonDatasource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import learning.persondb.PersonEntity
import javax.inject.Inject

@HiltViewModel
class PersonsListViewModel @Inject constructor(
    private val personDatasource: PersonDatasource
) : ViewModel() {

    val personsState = personDatasource.getAllPersons()

    var personDetail by mutableStateOf<PersonEntity?>(null)
        private set

    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    fun onInsertPersonClick() {
        if (firstName.isBlank() || lastName.isEmpty()) return

        viewModelScope.launch {
            personDatasource.insertPerson(
                firstName, lastName
            )

            firstName = ""
            lastName = ""
        }
    }

    fun onDeleteIconClick(id: Long) {
        viewModelScope.launch {
            personDatasource.deletePersonById(id)
        }
    }

    fun getPersonById(id: Long) {
        viewModelScope.launch {
            personDetail = personDatasource.getPersonById(id)
        }
    }

    fun onFirstNameChanged(value: String) {
        firstName = value
    }

    fun onLastNameChanged(value: String) {
        lastName = value
    }

    fun onPersonDetailsDismiss(){
        personDetail = null
    }

}