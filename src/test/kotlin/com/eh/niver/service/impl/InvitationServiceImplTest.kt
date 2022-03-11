package com.eh.niver.service.impl

import com.eh.niver.model.Group
import com.eh.niver.model.Invitation
import com.eh.niver.model.Person
import com.eh.niver.model.vo.RequestCreateHash
import com.eh.niver.model.vo.RequestUpdatePasswordPerson
import com.eh.niver.model.vo.RequestUpdatePerson
import com.eh.niver.model.vo.ResponseGroupInvitation
import com.eh.niver.repository.GroupRepository
import com.eh.niver.repository.InvitationRepository
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
class InvitationServiceImplTest {

    @MockK(relaxed = true)
    lateinit var personService: PersonServiceImpl

    @MockK(relaxed = true)
    lateinit var groupRepository: GroupRepository

    @MockK(relaxed = true)
    lateinit var repository: InvitationRepository

    @InjectMockKs
    lateinit var service: InvitationServiceImpl

    @DisplayName("should create a group invite")
    @Test
    fun createAndUpdateHash_ShouldCreateGroupInvite() {
        val groupRequest = RequestCreateHash(
            idGroup = 8,
            owner = 8
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
                members = mutableListOf(owner),
                invite = null
            )
        )

        val invitation = Invitation(
            uuidHash = UUID.randomUUID(),
            used = 0,
            creation = LocalDate.now(),
            group = group.get()
        )

        val responseResult = ResponseGroupInvitation(
            groupId = group.get().idGroup!!,
            groupName = group.get().name,
            ownerId = owner.idPerson,
            ownerName = owner.name,
            inviteHash = invitation.uuidHash.toString()
        )

        every { groupRepository.findById(any()) } returns group
        every { repository.save(any()) } returns invitation

        val result = service.createAndUpdateHash(groupRequest)

        verify(exactly = 0) { repository.deleteById(any()) }
        verify(exactly = 1) { repository.save(any()) }
        Assertions.assertEquals(responseResult, result)
    }

    @DisplayName("should update a group invite")
    @Test
    fun createAndUpdateHash_ShouldUpdateGroupInvite() {
        val groupRequest = RequestCreateHash(
            idGroup = 8,
            owner = 8
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
                members = mutableListOf(owner),
                invite = Invitation(
                    uuidHash = UUID.randomUUID(),
                    used = 0,
                    creation = LocalDate.now(),
                    group = Group(
                        idGroup = 8,
                        name = "Teste",
                        owner = owner,
                        members = mutableListOf(owner)
                    )
                )
            )
        )

        val invitation = Invitation(
            uuidHash = UUID.randomUUID(),
            used = 0,
            creation = LocalDate.now(),
            group = group.get()
        )


        val responseResult = ResponseGroupInvitation(
            groupId = group.get().idGroup!!,
            groupName = group.get().name,
            ownerId = owner.idPerson,
            ownerName = owner.name,
            inviteHash = invitation.uuidHash.toString()
        )

        every { groupRepository.findById(any()) } returns group
        every { repository.save(any()) } returns invitation

        val result = service.createAndUpdateHash(groupRequest)

        verify(exactly = 1) { repository.deleteById(any()) }
        verify(exactly = 1) { repository.save(any()) }
        Assertions.assertEquals(responseResult, result)
    }

    @DisplayName("should return exception if owner isn't the same of group")
    @Test
    fun createAndUpdateHash_ShouldReturnExceptionIfOwnerIsNotTheSameOfGroup() {
        val groupRequest = RequestCreateHash(
            idGroup = 8,
            owner = 9
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
                members = mutableListOf(owner),
                invite = null
            )
        )

        every { groupRepository.findById(group.get().idGroup!!) } returns group

        verify(exactly = 0) { repository.save(any()) }
        assertThrows<Exception> { service.createAndUpdateHash(groupRequest) }
    }

    @DisplayName("should return exception if group not exists")
    @Test
    fun createAndUpdateHash_ShouldReturnExceptionIfGroupNotExists() {
        val groupRequest = RequestCreateHash(
            idGroup = 8,
            owner = 9
        )

        every { groupRepository.findById(any()) } throws Exception()

        verify(exactly = 0) { repository.save(any()) }
        assertThrows<Exception> { service.createAndUpdateHash(groupRequest) }
    }

    @DisplayName("should return invite by group id")
    @Test
    fun getInviteByGroupId_ShouldReturnInviteByGroupId() {
        val idGroupRequest = 8L
        val idOwnerRequest = 8L

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
                invite = Invitation(
                    uuidHash = UUID.randomUUID(),
                    used = 0,
                    creation = LocalDate.now(),
                    group = Group(
                        idGroup = 8,
                        name = "Teste",
                        owner = owner,
                        members = mutableListOf(owner)
                    )
                )
            )
        )

        val responseResult = ResponseGroupInvitation(
            groupId = group.get().idGroup!!,
            groupName = group.get().name,
            ownerId = owner.idPerson,
            ownerName = owner.name,
            inviteHash = group.get().invite!!.uuidHash.toString()
        )

        every { groupRepository.findById(any()) } returns group
        every { repository.findInvitationByGroup(any()) } returns group.get().invite!!

        val result = service.getInviteByGroupId(idGroupRequest, idOwnerRequest)

        verify(exactly = 0) { repository.save(any()) }
        Assertions.assertEquals(responseResult, result)
    }

    @DisplayName("should return exception if owner isn't the same of group")
    @Test
    fun getInviteByGroupId_ShouldReturnExceptionIfOwnerIsNotTheSameOfGroup() {
        val idGroupRequest = 8L
        val idOwnerRequest = 9L

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

        every { groupRepository.findById(group.get().idGroup!!) } returns group

        verify(exactly = 0) { repository.save(any()) }
        assertThrows<Exception> { service.getInviteByGroupId(idGroupRequest, idOwnerRequest) }
    }

    @DisplayName("should return exception if group not exists")
    @Test
    fun getInviteByGroupId_ShouldReturnExceptionIfGroupNotExists() {
        val idGroupRequest = 8L
        val idOwnerRequest = 9L

        every { groupRepository.findById(any()) } throws Exception()

        verify(exactly = 0) { repository.save(any()) }
        assertThrows<Exception> { service.getInviteByGroupId(idGroupRequest, idOwnerRequest) }
    }

    @DisplayName("should return group by invitation hash")
    @Test
    fun getGroupByInvitationHash_ShouldReturnGroupByHash() {
        val hashRequest = UUID.randomUUID()

        val owner = Person(
            idPerson = 8,
            name = "Joselito",
            birthday = LocalDate.now(),
            email = "teste@teste.com",
            password = "joselito123"
        )

        val group = Group(
            idGroup = 8,
            name = "Teste",
            owner = owner,
            members = mutableListOf(owner),
            invite = Invitation(
                uuidHash = hashRequest,
                used = 0,
                creation = LocalDate.now(),
                group = Group(
                    idGroup = 8,
                    name = "Teste",
                    owner = owner,
                    members = mutableListOf(owner)
                )
            )
        )


        val responseResult = ResponseGroupInvitation(
            groupId = group.idGroup!!,
            groupName = group.name,
            ownerId = owner.idPerson,
            ownerName = owner.name,
        )

        every { repository.findById(any()).get().group } returns group

        val result = service.getGroupByInvitationHash(hashRequest)

        verify(exactly = 0) { repository.save(any()) }
        Assertions.assertEquals(responseResult, result)
    }

    @DisplayName("should return exception if group not exists")
    @Test
    fun getGroupByInvitationHash_ShouldReturnExceptionIfGroupNotExists() {
        val hashRequest = UUID.randomUUID()

        every { repository.findById(any()).get().group } throws Exception()

        verify(exactly = 0) { repository.save(any()) }
        assertThrows<Exception> { service.getGroupByInvitationHash(hashRequest)}
    }

    @DisplayName("should update group used with success")
    @Test
    fun updateUsedGroupInvite_ShouldUpdateGroupUsedWithSuccess() {
        val hashRequest = UUID.randomUUID()

        val inv = Invitation(
            uuidHash = hashRequest,
            used = 0,
            creation = LocalDate.now(),
            group = Group(
                idGroup = 8,
                name = "Teste",
                owner = Person(
                    idPerson = 8,
                    name = "Joselito",
                    birthday = LocalDate.now(),
                    email = "teste@teste.com",
                    password = "joselito123"
                )
            )
        )
        every { repository.getById(any()) } returns inv
        every { repository.save(any()) } returns inv
        every { groupRepository.getById(any()) } returns inv.group

        service.updateUsedGroupInvite(hashRequest)

        verify(exactly = 1) { repository.save(any()) }

    }

    @DisplayName("should return exception if invitation not exists")
    @Test
    fun updateUsedGroupInvite_ShouldReturnExceptionIfInvitationNotExists() {
        val hashRequest = UUID.randomUUID()

        every { repository.getById(any()) } throws Exception()

        verify(exactly = 0) { repository.save(any()) }
        assertThrows<Exception> { service.updateUsedGroupInvite(hashRequest)}
    }

}