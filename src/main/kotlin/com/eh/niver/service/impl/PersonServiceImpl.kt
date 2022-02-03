package com.eh.niver.service.impl

import com.eh.niver.model.Person
import com.eh.niver.repository.PersonRepository
import com.eh.niver.service.PersonService
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonServiceImpl(val repository: PersonRepository): PersonService {
    override fun getPersonById(idPerson: Long): Optional<Person> {
        return repository.findByIdPerson(idPerson)
    }

}