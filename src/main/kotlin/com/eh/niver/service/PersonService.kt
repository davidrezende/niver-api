package com.eh.niver.service

import com.eh.niver.model.Person
import java.util.*

interface PersonService {
    fun getPersonById(idPerson: Long): Person
    fun getBirthdaysToday(): List<Person>?
    fun getPersonByEmail(email: String): Optional<Person>
    fun savePerson(person: Person): Person
    fun updatePerson(person: Person): Person
    fun deletePerson(personId: String)
}