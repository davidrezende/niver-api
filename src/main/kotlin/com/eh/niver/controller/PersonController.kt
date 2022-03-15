package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.model.vo.RequestUpdatePasswordPerson
import com.eh.niver.model.vo.RequestUpdatePerson
import com.eh.niver.service.PersonService
import com.eh.niver.service.UserService
import com.eh.niver.service.impl.CalendarServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@Tag(name= "Pessoa")
@RestController
@SecurityRequirement(name = "niverapi")
@RequestMapping("person/api")
class PersonController(val personService: PersonService) {

    companion object {
        private val logger = LoggerFactory.getLogger(PersonController::class.java)
    }

    @Autowired
    private lateinit var userService: UserService

    @Operation(summary = "Procura pessoa por Id.")
    @GetMapping("/id/{idPerson}")
    fun searchPersonById(@PathVariable idPerson: Long): Person {
        return personService.getPersonById(idPerson)
    }

    @Operation(summary = "Procura uma pessoa por email.")
    @GetMapping("/email/{email}")
    fun searchPersonByEmail(@PathVariable email: String): Optional<Person> {
        return personService.getPersonByEmail(email)
    }

    @Operation(summary = "Procura aniversáriantes do dia.")
    @GetMapping("/birthdays")
    fun searchBirthdaysByToday(): List<Person>? {
        val today = LocalDate.now()
        return personService.getBirthdaysToday()
    }

    @Operation(summary = "Salva uma pessoa.")
    @PostMapping()
    fun savePerson(@RequestBody person: Person): Person {
        return userService.create(person)
    }

    @Operation(summary = "Atualiza uma pessoa.")
    @PutMapping
    fun updatePerson(@RequestBody request: RequestUpdatePerson): Person {
        logger.info("Atualizando uma pessoa: ${request.idPerson}")
        return personService.updatePerson(request)
    }

    @Operation(summary = "Atualiza a senha de uma pessoa.")
    @PutMapping("/password")
    fun updatePasswordPerson(@RequestBody request: RequestUpdatePasswordPerson): Person {
        logger.info("Atualizando uma pessoa: ${request.idPerson}")
        return personService.updatePasswordPerson(request)
    }

    @Operation(summary = "Deleta uma pessoa.")
    @DeleteMapping("/{personId}")
    fun deletePerson(@PathVariable personId: String) {
        return personService.deletePerson(personId)
    }

}