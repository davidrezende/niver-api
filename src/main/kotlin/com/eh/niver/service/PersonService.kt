package com.eh.niver.service

import com.eh.niver.model.Person
import java.util.*

interface PersonService {
    fun getPersonById(idPerson: Long): Optional<Person>
}