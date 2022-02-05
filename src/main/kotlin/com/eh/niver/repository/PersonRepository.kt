package com.eh.niver.repository

import com.eh.niver.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface PersonRepository : JpaRepository<Person, Long> {
    fun findByEmail(email: String): Person
    fun findByBirthday(birthday: LocalDate): List<Person>
    fun findByIdPerson(idPerson: Long): Optional<Person>
}
