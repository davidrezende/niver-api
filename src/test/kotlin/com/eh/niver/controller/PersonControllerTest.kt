package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.repository.PersonRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate


class PersonControllerTest {
    private val personRepository: PersonRepository = mockk()
    private val controller: PersonController = mockk()
    @Test
    @DisplayName("save person with sucess")
    fun savePerson_thenReturnSucess() {

        val personMockRequest = Person(
            idPerson = null,
            name = "MockTest",
            birthday = LocalDate.now(),
            email = "test@mock.com",
            password = "mockPass"
        )
        //given
        val personMockReturn = Person(
            idPerson = 1,
            name = "MockTest",
            birthday = LocalDate.now(),
            email = "test@mock.com",
            password = "mockPass"
        )

        every { personRepository.save(personMockRequest) } returns personMockReturn;

        //when
        val result = personRepository.save(personMockRequest);

        //then
        verify(exactly = 1) { personRepository.save(personMockRequest) };
        assertEquals(personMockReturn, result)
    }

    @Test
    fun somaERetorna4_thenReturn4(){
        val n1 = 2
        val n2 = 2
        every { controller.somaERetorna4(n1, n2) } returns 4;

        //when
        val result = controller.somaERetorna4(n1, n2);

        //then
        verify(exactly = 1) { controller.somaERetorna4(n1, n2) };
        assertEquals(4, result)
    }

    @Test
    fun somaERetorna4_thenReturnDifferent4(){
        val n1 = 3
        val n2 = 2
        every { controller.somaERetorna4(n1, n2) } returns 5;

        //when
        val result = controller.somaERetorna4(n1, n2);

        //then
        verify(exactly = 1) { controller.somaERetorna4(n1, n2) };
        assertEquals(5, result)
    }
}