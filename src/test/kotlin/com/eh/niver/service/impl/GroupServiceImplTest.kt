package com.eh.niver.service.impl

import com.eh.niver.model.Group
import com.eh.niver.model.Person
import com.eh.niver.model.vo.*
import com.eh.niver.repository.GroupRepository
import com.eh.niver.service.InvitationService
import com.eh.niver.service.PersonService
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
class GroupServiceImplTest {

    @MockK(relaxed = true)
    lateinit var repository : GroupRepository

    @MockK(relaxed = true)
    lateinit var personService : PersonService

    @MockK(relaxed = true)
    lateinit var invitationService : InvitationService

    @InjectMockKs
    lateinit var service: GroupServiceImpl

    @DisplayName("should delete a group with success")
    @Test
    fun deleteGroup_ShouldDeleteGroup() {
        val groupId = "8"

        val groupReturn = Unit

        every { repository.deleteById(any()) } returns Unit

        val result = service.deleteGroup(groupId)

        Assertions.assertEquals(groupReturn,result)
    }

    @DisplayName("should save a group with success")
    @Test
    fun saveGroup_ShouldSaveGroup() {
        val groupRequest = RequestSaveGroup(
            idGroup = null,
            name = "Teste",
            owner = ResponseMember(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now()
            )
        )

        val owner = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val groupReturn = Group(
            idGroup = null,
            name = "Teste",
            owner = owner,
            members = mutableListOf(owner)
        )

        every { personService.getPersonById(any()) } returns owner
        every { repository.save(any()) } returns groupReturn

        val result = service.saveGroup(groupRequest)

        Assertions.assertEquals(groupReturn,result)
    }

    @DisplayName("should return a exception if the group was not created")
    @Test
    fun saveGroup_ShouldReturnExceptionWhenFailed() {
        val groupRequest = RequestSaveGroup(
            idGroup = null,
            name = "Teste",
            owner = ResponseMember(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now()
            )
        )

        val owner = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val groupReturn = Group(
            idGroup = null,
            name = "Teste",
            owner = owner,
            members = mutableListOf(owner)
        )

        every { personService.getPersonById(any()) } throws Exception()
        every { repository.save(any()) } returns groupReturn

        assertThrows<Exception> { service.saveGroup(groupRequest) }
        verify(exactly = 0) { repository.save(any()) }
    }

    @DisplayName("should return a group by id")
    @Test
    fun getGroupById_ShouldReturnGroupById() {
        val groupId = "8"

        val owner = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val group = Optional.of(Group(
            idGroup = 8,
            name = "Teste",
            owner = owner,
            members = mutableListOf(owner),
            invite = null
        ))

        val groupResult = ResponseGroup(
            idGroup = 8,
            name = "Teste",
            owner = ResponseMember(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now()
            ),
            members = mutableListOf(owner).map { ResponseMember(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now()
            ) }
        )
        every { repository.findById(any()) } returns group

        val result = service.getGroupById(groupId)

        Assertions.assertEquals(groupResult,result)
    }

    @DisplayName("should return exception when group is empty")
    @Test
    fun getGroupById_ShouldReturnExceptionWhenGroupIsEmpty() {
        val groupId = "8"

        val owner = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val group = Optional.of(Group(
            idGroup = 8,
            name = "Teste",
            owner = owner,
            members = mutableListOf(owner),
            invite = null
        ))

        val groupResult = ResponseGroup(
            idGroup = 8,
            name = "Teste",
            owner = ResponseMember(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now()
            ),
            members = mutableListOf(owner).map { ResponseMember(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now()
            ) }
        )
        every { repository.findById(any()) } returns Optional.empty()

        assertThrows<Exception> { service.getGroupById(groupId) }
    }

    @DisplayName("should update a group with success")
    @Test
    fun updateGroup_ShouldReturnUpdatedGroup() {
        val groupRequest = RequestSaveGroup(
            idGroup = 1,
            name = "Testando",
            owner = ResponseMember(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now()
            )
        )

        val person = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val group = Optional.of(Group(
            idGroup = 1,
            name = "Teste",
            owner = person,
            members = mutableListOf(person)
        ))

        val groupReturn = Group(
            idGroup = groupRequest.idGroup,
            name = groupRequest.name,
            owner = person,
            members = mutableListOf(person)
        )

        every { personService.getPersonById(any()) } returns person
        every { repository.findById(any()) } returns group
        every { repository.save(any()) } returns groupReturn

        val result = service.updateGroup(groupRequest)

        verify(exactly = 1) { repository.save(groupReturn) }
        Assertions.assertEquals(groupReturn,result)

    }

    @DisplayName("should return all groups by member")
    @Test
    fun searchAllGroupsByMember_ShouldReturnAllGroupsByMember() {
        val personRequest = 8L

        val person = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val groups = listOf(Group(
            idGroup = 1,
            name = "Teste",
            owner = person,
            members = mutableListOf(person)
        ))

        val groupsReturn = groups.map {
            ResponseGroup(
                name = "Teste",
                idGroup = 1,
                owner = ResponseMember(
                    idPerson = person.idPerson!!,
                    name = person.name,
                    birthday = person.birthday
                ),
                members = mutableListOf(person).map { ResponseMember(
                    idPerson = 8,
                    name = "Joselito",
                    birthday = LocalDate.now()
                ) }
            )
        }

        every { personService.getPersonById(any()) } returns person
        every { repository.findByMembers(any()) } returns groups

        val result = service.searchAllGroupsByMember(personRequest)

        Assertions.assertEquals(groupsReturn,result)

    }

}