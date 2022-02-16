package com.eh.niver.service.impl

import com.eh.niver.model.vo.UserDetailsImpl
import com.eh.niver.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl: UserDetailsService {

    @Autowired
    private lateinit var userRepository: PersonRepository


    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException(email)

        return UserDetailsImpl(user)
    }
}