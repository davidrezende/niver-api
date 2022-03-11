package com.eh.niver.service.impl

import com.eh.niver.model.Group
import com.eh.niver.model.Invitation
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
    lateinit var repository: GroupRepository

    @MockK(relaxed = true)
    lateinit var personService: PersonService

    @MockK(relaxed = true)
    lateinit var invitationService: InvitationService

    @InjectMockKs
    lateinit var service: GroupServiceImpl

    @DisplayName("should delete a group with success")
    @Test
    fun deleteGroup_ShouldDeleteGroup() {
        val groupId = "8"

        every { repository.deleteById(any()) } returns Unit

        service.deleteGroup(groupId)

        verify(exactly = 1) { repository.deleteById(groupId.toLong()) }
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
            idGroup = 1,
            name = "Teste",
            owner = owner,
            members = mutableListOf(owner)
        )

        every { personService.getPersonById(any()) } returns owner
        every { repository.save(any()) } returns groupReturn

        val result = service.saveGroup(groupRequest)

        Assertions.assertEquals(groupReturn, result)
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

        every { personService.getPersonById(any()) } throws Exception()

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

        val group = Optional.of(
            Group(
                idGroup = 8,
                name = "Teste",
                owner = owner,
                members = mutableListOf(owner),
                invite = null
            )
        )

        val groupResult = ResponseGroup(
            idGroup = 8,
            name = "Teste",
            owner = ResponseMember(
                idPerson = owner.idPerson!!,
                name = owner.name,
                birthday = owner.birthday
            ),
            members = mutableListOf(
                ResponseMember(
                    idPerson = owner.idPerson!!,
                    name = owner.name,
                    birthday = owner.birthday
                )
            )
        )
        every { repository.findById(any()) } returns group

        val result = service.getGroupById(groupId)

        Assertions.assertEquals(groupResult, result)
    }

    @DisplayName("should return exception when group not exists")
    @Test
    fun getGroupById_ShouldReturnExceptionWhenGroupNotExists() {
        val groupId = "8"

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

        val group = Optional.of(
            Group(
                idGroup = 1,
                name = "Teste",
                owner = person,
                members = mutableListOf(person)
            )
        )

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
        Assertions.assertEquals(groupReturn, result)

    }

    @DisplayName("should return a exception if person not exists")
    @Test
    fun updateGroup_ShouldReturnExceptionIfPersonNotExists() {
        val groupRequest = RequestSaveGroup(
            idGroup = 1,
            name = "Testando",
            owner = ResponseMember(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now()
            )
        )

        every { personService.getPersonById(groupRequest.owner.idPerson) } throws Exception()
        verify(exactly = 0) { repository.findById(groupRequest.idGroup!!) }
        verify(exactly = 0) { repository.save(any()) }

        assertThrows<Exception> { service.updateGroup(groupRequest) }
    }

    @DisplayName("should return a exception if group not exists")
    @Test
    fun updateGroup_ShouldReturnExceptionIfGroupNotExists() {
        val groupRequest = RequestSaveGroup(
            idGroup = 1,
            name = "Testando",
            owner = ResponseMember(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now()
            )
        )

        every { repository.findById(groupRequest.idGroup!!) } throws Exception()
        verify(exactly = 0) { repository.save(any()) }
        verify(exactly = 0) { personService.getPersonById(groupRequest.owner.idPerson) }

        assertThrows<Exception> { service.updateGroup(groupRequest) }
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

        val groups = listOf(
            Group(
                idGroup = 1,
                name = "Teste",
                owner = person,
                members = mutableListOf(person)
            )
        )

        val groupsReturn = groups.map {
            ResponseGroup(
                name = "Teste",
                idGroup = 1,
                owner = ResponseMember(
                    idPerson = person.idPerson!!,
                    name = person.name,
                    birthday = person.birthday
                ),
                members = mutableListOf(
                    ResponseMember(
                        idPerson = person.idPerson!!,
                        name = person.name,
                        birthday = person.birthday
                    )
                )
            )
        }

        every { personService.getPersonById(any()) } returns person
        every { repository.findByMembers(any()) } returns groups

        val result = service.searchAllGroupsByMember(personRequest)

        Assertions.assertEquals(groupsReturn, result)

    }

    @DisplayName("should return exception when person not exists")
    @Test
    fun searchAllGroupsByMember_ShouldReturnExceptionWhenPersonNotExists() {
        val personId = "8"

        every { personService.getPersonById(any()) } throws Exception()
        verify(exactly = 0) { repository.findByMembers(any()) }

        assertThrows<Exception> { service.searchAllGroupsByMember(personId.toLong()) }
    }

    @DisplayName("should return exception when group not exists")
    @Test
    fun searchAllGroupsByMember_ShouldReturnExceptionWhenGroupNotExists() {
        val personId = "8"

        val person = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        every { personService.getPersonById(any()) } returns person
        every { repository.findByMembers(any()) } throws Exception()

        assertThrows<Exception> { service.searchAllGroupsByMember(personId.toLong()) }
    }

    @DisplayName("Should return all members by group id")
    @Test
    fun searchAllMembersByGroup_ShouldReturnAllMembersByGroupId() {
        val groupId = 8L

        val membersReturn = mutableListOf(
            Person(
                idPerson = 8,
                name = "Joselito",
                birthday = LocalDate.now(),
                email = "teste@teste.com",
                password = "joselito123"
            )
        )

        every { repository.findById(groupId).get().members } returns membersReturn

        val result = service.searchAllMembersByGroup(groupId)

        Assertions.assertEquals(membersReturn, result)
    }

    @DisplayName("should return exception when group not exists")
    @Test
    fun searchAllMembersByGroup_ShouldReturnExceptionWhenGroupNotExists() {
        val groupId = 8L

        every { repository.findById(groupId).get().members } throws Exception()
        verify(exactly = 0) { repository.findByMembers(any()) }

        assertThrows<Exception> { service.searchAllMembersByGroup(groupId) }
    }

    @DisplayName("should save member in group with success")
    @Test
    fun saveMemberInGroup_ShouldSaveMemberWithSuccess() {
        val member = RequestSaveMember(
            idPerson = 9,
            idGroup = 8,
            hash = UUID.randomUUID()
        )

        val newMember = Person(
            idPerson = 9,
            name = "teste",
            birthday = LocalDate.now(),
            email = "testa@teste.com",
            password = "teste"
        )

        val owner = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val group = Optional.of(
            Group(
                idGroup = 8,
                name = "Teste",
                owner = owner,
                members = mutableListOf(owner)
            )
        )

        val groupReturn = Group(
            idGroup = group.get().idGroup,
            name = group.get().name,
            owner = group.get().owner,
            members = mutableListOf(owner, newMember)
        )

        every { repository.findById(any()) } returns group
        every { personService.getPersonById(any()) } returns newMember
        every { repository.save(any()) } returns group.get()

        service.saveMemberInGroup(member)

        verify(exactly = 1) { repository.save(groupReturn) }
    }

    @DisplayName("should return exception when group not exists")
    @Test
    fun saveMemberInGroup_ShouldReturnExceptionWhenGroupNotExists() {
        val member = RequestSaveMember(
            idPerson = 9,
            idGroup = 8,
            hash = UUID.randomUUID()
        )

        every { repository.findById(member.idGroup) } returns Optional.empty()
        verify(exactly = 0) { personService.getPersonById(member.idPerson) }

        assertThrows<Exception> { service.saveMemberInGroup(member) }
    }

    @DisplayName("should return exception when person not exists")
    @Test
    fun saveMemberInGroup_ShouldReturnExceptionWhenPersonNotExists() {
        val member = RequestSaveMember(
            idPerson = 9,
            idGroup = 8,
            hash = UUID.randomUUID()
        )

        every { personService.getPersonById(member.idPerson) } throws Exception()
        verify(exactly = 0) { repository.findById(member.idGroup) }

        assertThrows<Exception> { service.saveMemberInGroup(member) }
    }

    @DisplayName("should delete a member with success")
    @Test
    fun deleteMemberInGroup_ShouldDeleteMember() {
        val idPerson = "9"
        val idGroup = "8"

        val owner = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val member = Person(
            idPerson = 9,
            name = "teste",
            birthday = LocalDate.now(),
            email = "testa@teste.com",
            password = "teste"
        )

        val group = Group(
            idGroup = 8,
            name = "Teste",
            owner = owner,
            members = mutableListOf(owner, member)

        )

        every { repository.findById(idGroup.toLong()) } returns Optional.of(group)
        every { repository.save(group) } returns group

        service.deleteMemberInGroup(idPerson, idGroup)

        verify(exactly = 1) {
            repository.save(group)
        }
    }

    @DisplayName("should return exception when group not exists")
    @Test
    fun deleteMemberInGroup_ShouldReturnExceptionWhenGroupNotExists() {
        val idPerson = "9"
        val idGroup = "10"

        every { repository.findById(idGroup.toLong()) } returns Optional.empty()
        verify(exactly = 0) { repository.save(any()) }

        assertThrows<Exception> { service.deleteMemberInGroup(idPerson,idGroup) }
    }
}