package com.eh.niver.service.impl

import com.eh.niver.model.Person
import com.eh.niver.repository.PersonRepository
import com.eh.niver.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class PersonServiceImpl(val repository: PersonRepository): PersonService {

    companion object{
        private val logger = LoggerFactory.getLogger(PersonServiceImpl::class.java)
    }

    override fun getPersonById(idPerson: Long): Optional<Person> {
        return repository.findByIdPerson(idPerson)
    }

    override fun getBirthdaysToday(): List<Person>? {
        logger.info("M=getBirthdaysToday msg=Buscando aniversariantes do dia")
        val today = LocalDate.now()
        return repository.findByBirthdaysForToday(today)
    }

}