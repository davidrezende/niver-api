package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.repository.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("person/api")
class PersonController(val repository: PersonRepository) {

    companion object{
        private val logger = LoggerFactory.getLogger(PersonController::class.java)
    }

    @GetMapping("/id/{idPerson}")
    fun searchPersonById(@PathVariable idPerson: Long): Person {
        logger.info("Procurando pessoa por ID: $idPerson")
        return repository.getById(idPerson)
    }

    @GetMapping("/email/{email}")
    fun searchPersonByEmail(@PathVariable email: String): Person {
        logger.info("Procurando pessoa por Email: $email")
        return repository.findByEmail(email)
    }

    @GetMapping("/birthdays")
    fun searchBirthdaysByToday(): List<Person> {
        val today = LocalDate.now()
        logger.info("Procurando aniversários hoje: $today")
        return repository.findByBirthday(today)
    }

    @PostMapping()
    fun savePerson(@RequestBody person: Person): Person {
        logger.info("Salvando uma pessoa: $person")
        return repository.save(person)
    }

    @PutMapping()
    fun updatePerson(@RequestBody person: Person): Person {
        logger.info("Atualizando uma pessoa: $person")
        return repository.save(person)
    }

    @DeleteMapping("/{personId}")
    fun deletePerson(@PathVariable personId: String) {
        logger.info("Deletando uma pessoa $personId")
        return repository.deleteById(personId.toLong())
    }

}