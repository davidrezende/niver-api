package com.eh.niver.controller

import com.eh.niver.exception.handling.ExceptionHandlingController
import com.eh.niver.model.Person
import com.eh.niver.model.vo.RequestUpdatePerson
import com.eh.niver.service.PersonService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate
import javax.persistence.EntityNotFoundException


class PersonControllerTest {
    private val service = mockk<PersonService>(relaxed = true)
    private val controller = PersonController(service)
    private var mockMvc: MockMvc = MockMvcBuilders
        .standaloneSetup(controller)
        .setControllerAdvice(ExceptionHandlingController())
        .build()

    @Autowired
    var mapperBuilder: Jackson2ObjectMapperBuilder? = null

    @DisplayName("should return person by id")
    @Test
    fun searchPersonById_ShouldReturnPersonById() {
        val personResponse = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        every {service.getPersonById(any()) } returns personResponse

        mockMvc.get("/person/api/id/8")
            .andDo { MockMvcResultHandlers.print() }
            .andExpect {
                MockMvcResultMatchers.status().isOk
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$").isNotEmpty
                    jsonPath("$.idPerson").value(personResponse.idPerson)
                }
            }

        verify (exactly = 1){ controller.searchPersonById(8) }
    }

    @DisplayName("should return exception if person not exists")
    @Test
    fun searchPersonById_ShouldReturnExceptionIfPersonNotExists() {
        every {service.getPersonById(any()) } throws EntityNotFoundException()

        mockMvc.get("/person/api/id/8")
            .andDo { MockMvcResultHandlers.print() }
            .andExpect {
                MockMvcResultMatchers.status().isBadRequest
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }

        verify (exactly = 1){ controller.searchPersonById(8) }
    }

    @DisplayName("should update a person with success")
    @Test
    fun updatePerson_ShouldUpdatePersonWithSuccess() {
        val updatePersonRequest = RequestUpdatePerson(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            confirmPassword = "joselito123"
        )

        val personResponse = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val mapper = mapperBuilder!!.build<ObjectMapper>()
        every {service.updatePerson(any()) } returns personResponse

        mockMvc.put("/person/api"){
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(updatePersonRequest)
        }
            .andDo { MockMvcResultHandlers.print() }
            .andExpect {
                MockMvcResultMatchers.status().isOk
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$").isNotEmpty
                    jsonPath("$.idPerson").value(personResponse.idPerson)
                }
            }

        verify (exactly = 1){ controller.updatePerson(updatePersonRequest) }
    }
}