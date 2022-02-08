package com.eh.niver.service.impl

import com.eh.niver.model.Group
import com.eh.niver.model.vo.RequestSaveGroup
import com.eh.niver.model.vo.RequestSaveMember
import com.eh.niver.model.vo.ResponseGroup
import com.eh.niver.model.vo.ResponseMember
import com.eh.niver.repository.GroupRepository
import com.eh.niver.service.GroupService
import com.eh.niver.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GroupServiceImpl(val repository: GroupRepository, val personService: PersonService) : GroupService {

    companion object {
        private val logger = LoggerFactory.getLogger(GroupServiceImpl::class.java)
    }

    override fun saveGroup(group: RequestSaveGroup): Group {
        val person = personService.getPersonById(group.idOwner.toLong())
        logger.info("Pessoa encontrada pelo id: ${group.idOwner}")
        val parseGroup = Group(
            idGroup = null,
            name = group.name,
            owner = person,
            members = mutableListOf(person)
        )
        return repository.save(parseGroup)
    }

    override fun deleteGroup(groupId: String) {
        return repository.deleteById(groupId.toLong())
    }

    override fun getGroupById(groupId: String): ResponseGroup {
        val group = repository.findById(groupId.toLong())
        if (group.isEmpty) {
            throw Exception("Group does not exist!")
        }
        return ResponseGroup(
            idGroup = group.get().idGroup,
            name = group.get().name,
            owner = group.get().owner.idPerson,
            members = group.get().members?.map { oto ->
                ResponseMember(
                    id = oto.idPerson!!,
                    name = oto.name
                )
            }
        )
    }

    override fun updateGroup(group: RequestSaveGroup): Group {
        val person = personService.getPersonById(group.idOwner.toLong())
        logger.info("Pessoa encontrada pelo id: ${group.idOwner}")
        val parseGroup = Group(
            idGroup = group.idGroup,
            name = group.name,
            owner = person
        )
        return repository.save(parseGroup)
    }

    override fun saveMemberInGroup(member: RequestSaveMember) {
        val group = repository.findById(member.idGroup)
        val person = personService.getPersonById(member.idPerson)
        if (!group.isEmpty) {
            group.get().members?.add(person)
        }
        repository.save(group.get())
    }

}
