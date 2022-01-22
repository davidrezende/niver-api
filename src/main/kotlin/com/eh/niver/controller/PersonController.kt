package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.repository.PersonRepository
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("person/api")
class PersonController(val repository: PersonRepository) {

    @GetMapping("/id/{idPerson}")
    fun searchPersonById(@PathVariable idPerson: Long): Person {
        return repository.getById(idPerson)

    }

    @GetMapping("/email/{email}")
    fun searchPersonByEmail(@PathVariable email: String): Person{
        return repository.findByEmail(email)
    }

    @GetMapping("/birthdays/{birthday}")
    fun searchBirthdaysByDate(@PathVariable birthday: String): List<Person>{
        val parseDate = LocalDate.parse(birthday)
        return repository.findByBirthday(parseDate)
    }

    @PostMapping()
    fun savePerson(@RequestBody person: Person): Person{
        return repository.save(person)
    }
}