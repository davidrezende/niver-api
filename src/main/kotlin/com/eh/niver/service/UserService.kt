package com.eh.niver.service

import com.eh.niver.model.Person
import com.eh.niver.model.vo.UserDetailsImpl
import com.eh.niver.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService {

    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    fun create(person: Person): Person {
        person.password = bCryptPasswordEncoder.encode(person.password)
        return repository.save(person)
    }

    fun myself(): String? {
        return repository.findByEmail(getCurrentUserEmail()).get().name
    }

    private fun getCurrentUserEmail(): String {
        val user = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        return user.username
    }

}