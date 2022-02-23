package com.eh.niver.service

import com.eh.niver.model.Person
import com.eh.niver.model.vo.RequestUpdatePasswordPerson
import com.eh.niver.model.vo.RequestUpdatePerson
import java.util.*

interface PersonService {
    fun getPersonById(idPerson: Long): Person
    fun getBirthdaysToday(): List<Person>?
    fun getPersonByEmail(email: String): Optional<Person>
    fun savePerson(person: Person): Person
    fun updatePerson(request: RequestUpdatePerson): Person
    fun updatePasswordPerson(request: RequestUpdatePasswordPerson): Person
    fun deletePerson(personId: String)
    fun findByMonthlyBirthdays(): List<Person>?
}