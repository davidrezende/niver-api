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

    override fun getPersonById(idPerson: Long): Person {
        logger.info("Procurando pessoa por ID: $idPerson")
        val person = repository.findByIdPerson(idPerson)
        if(person.isEmpty){
            throw Exception("Pessoa não encontrada.")
        }
        return person.get()
    }

    override fun getBirthdaysToday(): List<Person>? {
        logger.info("M=getBirthdaysToday msg=Buscando aniversariantes do dia")
        val today = LocalDate.now()
        return repository.findByBirthdaysForToday(today)
    }

    override fun getPersonByEmail(email: String): Optional<Person> {
        logger.info("Procurando pessoa por Email: $email")
        return repository.findByEmail(email)
    }

    override fun savePerson(person: Person): Person {
        logger.info("Salvando uma pessoa: $person")
        return repository.save(person)
    }

    override fun updatePerson(person: Person): Person {
        logger.info("Atualizando uma pessoa: $person")
        val personExists = repository.findByIdPerson(person.idPerson!!)
        if(personExists.isEmpty){
            throw Exception("Pessoa nao encontrada")
        }
        val personUpdated = personExists.get().apply {
            name = person.name
            email = person.email
            birthday = person.birthday
        }
        return repository.save(personUpdated)
    }

    override fun deletePerson(personId: String) {
        logger.info("Deletando uma pessoa $personId")
        return repository.deleteById(personId.toLong())
    }

    override fun findByMonthlyBirthdays(): List<Person>? {
        logger.info("Buscando aniversariantes do mês ${LocalDate.now()}.")
        return repository.findByMonthlyBirthdays()
    }

}