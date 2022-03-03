package com.eh.niver.service.impl

import com.eh.niver.model.Person
import com.eh.niver.repository.PersonRepository
import com.eh.niver.service.PersonService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(SpringExtension::class)
@Import(PersonServiceImpl::class)
class PersonServiceImplTest {
    @InjectMockKs
    val personService = mockk<PersonService>()
    @MockK
    lateinit var repository : PersonRepository

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
        val result = personService.getPersonById(idPerson)

        //validate
        Assertions.assertEquals(personReturn.get(), result)
    }


}