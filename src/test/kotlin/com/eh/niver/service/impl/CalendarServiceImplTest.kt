package com.eh.niver.service.impl

import com.eh.niver.model.Group
import com.eh.niver.model.Person
import com.eh.niver.model.vo.*
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
class CalendarServiceImplTest {

    @MockK(relaxed = true)
    private lateinit var personService: PersonServiceImpl

    @InjectMockKs
    private lateinit var service: CalendarServiceImpl

    @DisplayName("should return all birthdays from all members of all groups")
    @Test
    fun getBirthdaysFromAllMemberOfAllGroups_ShouldReturnAllBirthdays() {
        val monthRequest = 12
        val idPerson = 8L

        val person = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.of(2022,12,22),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val group = Group(
                idGroup = 8,
                name = "Teste",
                owner = person,
                members = mutableListOf(person)
        )

        val responseReturn = ResponseCalendarBirthdays(
            idPerson = person.idPerson!!,
            name = person.name,
            day = person.birthday.dayOfMonth
        )

        person.groups = listOf(group)
        every { personService.getPersonById(any()) } returns person

        val result = service.getBirthdaysFromAllMemberOfAllGroups(monthRequest,idPerson)
        Assertions.assertEquals(mutableListOf(responseReturn),result)

    }

}