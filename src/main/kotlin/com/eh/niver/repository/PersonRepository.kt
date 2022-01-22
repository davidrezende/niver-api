package com.eh.niver.repository

import com.eh.niver.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface PersonRepository : JpaRepository<Person, Long> {
    fun findByEmail(email: String): Person
    fun findByBirthday(birthday: LocalDate): List<Person>
}