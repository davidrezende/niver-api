package com.eh.niver.service

import com.eh.niver.model.Person

interface PersonService {
    fun getPersonById(idPerson: Long): Person
    fun getBirthdaysToday(): List<Person>?
    fun getPersonByEmail(email: String): Person
    fun savePerson(person: Person): Person
    fun deletePerson(personId: String)
}