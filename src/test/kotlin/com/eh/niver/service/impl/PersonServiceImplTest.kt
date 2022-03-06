package com.eh.niver.service.impl

import com.eh.niver.model.Person
import com.eh.niver.model.vo.RequestUpdatePerson
import com.eh.niver.repository.PersonRepository
import com.eh.niver.service.AuthenticationService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @DisplayName("should return exception if person not exists")
    @Test
    fun getPersonById_shouldReturnEmptyPerson() {
        val idPerson = 10L

        every { repository.findByIdPerson(any()) } returns Optional.empty()

        assertThrows<Exception> { service.getPersonById(idPerson) }
    }

    @DisplayName("should return all today birthdays")
    @Test
    fun getBirthdaysToday_shouldReturnTodayBirthdays(){
        val today = LocalDate.now()

        val listReturn = listOf(Person(
            idPerson = 8,
            name = "Joselito",
            birthday = today,
            email = "joselito@gmail.com",
            password = "joselito123"
        ))

        every { repository.findByBirthdaysForToday(today) } returns listReturn

        val result = service.getBirthdaysToday()

        Assertions.assertEquals(listReturn, result)
    }

    @DisplayName("should return person by email")
    @Test
    fun getPersonByEmail_shouldReturnPersonByEmail(){
        val email = "teste@teste.com"

        val personReturn = Optional.of(Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = email,
            password = "joselito123"
        ))

        every { repository.findByEmail(email) } returns personReturn

        val result = service.getPersonByEmail(email)

        Assertions.assertEquals(personReturn, result)
    }

    @DisplayName("should save with sucess")
    @Test
    fun savePerson_thenReturnSucess(){

        val person = Person(
            idPerson = null,
            name = "MockTest",
            birthday = LocalDate.now(),
            email = "test@mock.com",
            password = "mockPass"
        )
        //given
        val personReturn = Person(
            idPerson = 1,
            name = "MockTest",
            birthday = LocalDate.now(),
            email = "test@mock.com",
            password = "mockPass"
        )

        every { repository.save(person) } returns personReturn

        //when
        val result = service.savePerson(person)

        //then
        verify(exactly = 1) { repository.save(person) }
        Assertions.assertEquals(personReturn, result)
    }

    @DisplayName("should update a person with sucess")
    @Test
    fun updatePerson_shouldReturnUpdatedPerson(){
        val person = Person(
            idPerson = 1,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val personUpdated = RequestUpdatePerson(
            idPerson = 1,
            name = "LitoJose",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            confirmPassword = "joselito123"
        )

        val personReturn = person.apply { name = "LitoJose" }

        every { service.getPersonById(person.idPerson!!) } returns person
        every { authenticationService.authenticate(person.email,personUpdated.confirmPassword) } returns Unit
        every { repository.save(any()) } returns personReturn
        val result = service.updatePerson(personUpdated)

        verify(exactly = 1) { repository.save(person) }
        Assertions.assertEquals(personReturn, result)

    }

    @DisplayName("Should return exception when authentication failed")
    @Test
    fun updatePerson_shouldReturnExceptionWhenAuthFailed(){

        val person = Person(
            idPerson = 1,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val personUpdated = RequestUpdatePerson(
            idPerson = 1,
            name = "LitoJose",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            confirmPassword = "joselito123"
        )

        val personReturn = person.apply { name = "LitoJose" }

        every { service.getPersonById(person.idPerson!!) } returns person
        every { authenticationService.authenticate(person.email,personUpdated.confirmPassword) } throws Exception()

        assertThrows<Exception> { service.updatePerson(personUpdated) }
        verify(exactly = 0) { repository.save(any()) }
    }
}