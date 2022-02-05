package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.repository.GroupRepository
import com.eh.niver.repository.PersonRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Api(value = "Endpoints de pessoa.")
@RestController
@RequestMapping("person/api")
class PersonController(val repository: PersonRepository, val repositoryGroup: GroupRepository ) {

    companion object{
        private val logger = LoggerFactory.getLogger(PersonController::class.java)
    }

    @ApiOperation(value = "Procura pessoa por Id.")
    @GetMapping("/id/{idPerson}")
    fun searchPersonById(@PathVariable idPerson: Long): Person {
        logger.info("Procurando pessoa por ID: $idPerson")
        return repository.getById(idPerson)
    }

    @ApiOperation(value = "Procura uma pessoa por email.")
    @GetMapping("/email/{email}")
    fun searchPersonByEmail(@PathVariable email: String): Person {
        logger.info("Procurando pessoa por Email: $email")
        return repository.findByEmail(email)
    }

    @ApiOperation(value = "Procura aniversáriantes do dia.")
    @GetMapping("/birthdays")
    fun searchBirthdaysByToday(): List<Person> {
        val today = LocalDate.now()
        logger.info("Procurando aniversários hoje: $today")
        return repository.findByBirthday(today)
    }

    @ApiOperation(value = "Salva uma pessoa.")
    @PostMapping()
    fun savePerson(@RequestBody person: Person): Person {
        logger.info("Salvando uma pessoa: $person")
        return repository.save(person)
    }

    @ApiOperation(value = "Atualiza uma pessoa.")
    @PutMapping()
    fun updatePerson(@RequestBody person: Person): Person {
        logger.info("Atualizando uma pessoa: $person")
        return repository.save(person)
    }

    @ApiOperation(value = "Deleta uma pessoa.")
    @DeleteMapping("/{personId}")
    fun deletePerson(@PathVariable personId: String) {
        logger.info("Deletando uma pessoa $personId")
        return repository.deleteById(personId.toLong())
    }

    fun somaERetorna4(n1: Int, n2: Int): Int{
        val result = n1 + n2
        return if(result == 4){
            println("sucesso")
            result
        }else{
            println("ops")
            result
        }
    }

}