package com.eh.niver.service.impl

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
class MemberServiceImplTest {

    @MockK(relaxed = true)
    lateinit var personService: PersonServiceImpl

    @MockK(relaxed = true)
    lateinit var groupService: GroupServiceImpl

    @InjectMockKs
    lateinit var service: MemberServiceImpl

    @DisplayName("should save member with success")
    @Test
    fun saveMemberInGroup_ShouldSaveMemberWithSuccess() {
        val memberRequest = RequestSaveMember(
            idPerson = 8,
            idGroup = 8,
            hash = UUID.randomUUID()
        )

        service.saveMemberInGroup(memberRequest)
        verify(exactly = 1) { groupService.saveMemberInGroup(memberRequest) }
    }

    @DisplayName("should delete member with success")
    @Test
    fun deleteMemberInGroup_ShouldDeleteMemberWithSuccess() {
            val idPerson = "8"
            val idGroup = "8"

        service.deleteMemberInGroup(idPerson, idGroup)
        verify(exactly = 1) { groupService.deleteMemberInGroup(idPerson,idGroup) }
    }

    @DisplayName("should return all groups by member")
    @Test
    fun searchAllGroupsByMember_ShouldReturnAllGroupsByMember() {
        val idPerson = 8L

        val listReturn = listOf(ResponseGroup(
            name = "churrasco",
            idGroup = 8L
        ))
        every { groupService.searchAllGroupsByMember(any()) } returns listReturn
        val result = service.searchAllGroupsByMember(idPerson)

        Assertions.assertEquals(listReturn,result)
        verify(exactly = 1) { groupService.searchAllGroupsByMember(idPerson) }
    }

}