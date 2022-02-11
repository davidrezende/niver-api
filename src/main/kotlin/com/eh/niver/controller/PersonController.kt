package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.service.PersonService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Api(value = "Endpoints de pessoa.")
@RestController
@RequestMapping("person/api")
class PersonController(val personService: PersonService) {

    companion object {
        private val logger = LoggerFactory.getLogger(PersonController::class.java)
    }

    @ApiOperation(value = "Procura pessoa por Id.")
    @GetMapping("/id/{idPerson}")
    fun searchPersonById(@PathVariable idPerson: Long): Person {
        return personService.getPersonById(idPerson)
    }

    @ApiOperation(value = "Procura uma pessoa por email.")
    @GetMapping("/email/{email}")
    fun searchPersonByEmail(@PathVariable email: String): Person {
        return personService.getPersonByEmail(email)
    }

    @ApiOperation(value = "Procura aniversáriantes do dia.")
    @GetMapping("/birthdays")
    fun searchBirthdaysByToday(): List<Person>? {
        val today = LocalDate.now()
        return personService.getBirthdaysToday()
    }

    @ApiOperation(value = "Salva uma pessoa.")
    @PostMapping()
    fun savePerson(@RequestBody person: Person): Person {
        return personService.savePerson(person)
    }

    @ApiOperation(value = "Atualiza uma pessoa.")
    @PutMapping()
    fun updatePerson(@RequestBody person: Person): Person {
        logger.info("Atualizando uma pessoa: $person")
        return personService.savePerson(person)
    }

    @ApiOperation(value = "Deleta uma pessoa.")
    @DeleteMapping("/{personId}")
    fun deletePerson(@PathVariable personId: String) {
        return personService.deletePerson(personId)
    }

}