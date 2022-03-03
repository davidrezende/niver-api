package com.eh.niver.service.impl

import com.eh.niver.model.Person
import com.eh.niver.repository.PersonRepository
import com.eh.niver.service.AuthenticationService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
class PersonServiceImplTest {

    //Mockamos os parametros utilizados pelo construtor da nossa serviceImpl ( classe a ser testada nesse arquivo )
    @MockK(relaxed = true)
    lateinit var repository : PersonRepository

    @MockK
    lateinit var authenticationService: AuthenticationService

    //Apos mockar os dados que sao utilizados na nossa service acima, injetamos esse mocks na nossa service
    //representada abaixo por "service"
    //agora podemos realizar chamadas aos metodos da nossa classe alvo e testa-los
    @InjectMockKs
    lateinit var service: PersonServiceImpl

    @DisplayName("should return person by id")
    @Test
    fun getPersonById_shouldReturnPersonById() {
        //massa
        //input
        val idPerson: Long = 8
        //output
        val personReturn = Optional.of(Person(
            idPerson = idPerson,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "joselito@gmail.com",
            password = "joselito123"
        ))
        //fim massa

        //mock
        every { repository.findByIdPerson(idPerson) } returns personReturn

        //test
        val result = service.getPersonById(idPerson)

        //validate
        Assertions.assertEquals(personReturn.get(), result)
    }


}