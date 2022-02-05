package com.eh.niver.repository

import com.eh.niver.model.Group
import com.eh.niver.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GroupRepository : JpaRepository<Group, Long> {
    fun findGroupByOwner(owner: Optional<Person>): List<Group>
    fun findByMembers(person: Optional<Person>): List<Group>
}