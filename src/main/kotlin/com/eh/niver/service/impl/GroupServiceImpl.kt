package com.eh.niver.service.impl

import com.eh.niver.model.Group
import com.eh.niver.model.Person
import com.eh.niver.model.vo.RequestSaveGroup
import com.eh.niver.model.vo.RequestSaveMember
import com.eh.niver.model.vo.ResponseGroup
import com.eh.niver.model.vo.ResponseMember
import com.eh.niver.repository.GroupRepository
import com.eh.niver.service.GroupService
import com.eh.niver.service.InvitationService
import com.eh.niver.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GroupServiceImpl(val repository: GroupRepository, val personService: PersonService, val invitationService: InvitationService) : GroupService {

    companion object {
        private val logger = LoggerFactory.getLogger(GroupServiceImpl::class.java)
    }

    override fun saveGroup(group: RequestSaveGroup): Group {
        logger.info("Salvando um Grupo : $group")
        val person = personService.getPersonById(group.owner.idPerson)
        logger.info("Pessoa encontrada pelo id: ${group.owner.idPerson} - pessoa: $person")
        val parseGroup = Group(
            idGroup = null,
            name = group.name,
            owner = person,
            members = mutableListOf(person)
        )
        logger.info("grupo a ser salvo: $parseGroup")
        return repository.save(parseGroup)
    }

    override fun deleteGroup(groupId: String) {
        logger.info("Deletando um Grupo: $groupId")
        return repository.deleteById(groupId.toLong())
    }

    override fun getGroupById(groupId: String): ResponseGroup {
        logger.info("Procurando o grupo : $groupId")
        val group = repository.findById(groupId.toLong())
        if (group.isEmpty) {
            throw Exception("Group does not exist!")
        }
        return ResponseGroup(
            idGroup = group.get().idGroup,
            name = group.get().name,
            owner = ResponseMember(
                idPerson = group.get().owner.idPerson!!,
                name = group.get().owner.name,
                birthday = group.get().owner.birthday
            ),
            members = group.get().members?.map { oto ->
                ResponseMember(
                    idPerson = oto.idPerson!!,
                    name = oto.name,
                    birthday = oto.birthday
                )
            }
        )
    }

    override fun updateGroup(group: RequestSaveGroup): Group {
        logger.info("Alterando um Grupo: $group")
        val person = personService.getPersonById(group.owner.idPerson)
        logger.info("Pessoa encontrada pelo id: ${group.owner}")
        val grupo = repository.findById(group.idGroup!!)
        logger.info("Grupo encontrado pelo id: ${group.idGroup}")
        val parseGroup = Group(
            idGroup = group.idGroup,
            name = group.name,
            owner = person,
            members = grupo.get().members
        )
        return repository.save(parseGroup)
    }

    override fun searchAllGroupsByMember(personId: Long): List<ResponseGroup> {
        logger.info("Procurando grupos da pessoa: $personId")
        val person = personService.getPersonById(personId)
        val groups = repository.findByMembers(person)
        return groups.map {
            ResponseGroup(
                name = it.name,
                idGroup = it.idGroup,
                owner = ResponseMember(
                        idPerson = it.owner.idPerson!!,
                        name = it.owner.name,
                        birthday = it.owner.birthday
                ),
                members = it.members?.map { oto ->
                    ResponseMember(
                        idPerson = oto.idPerson!!,
                        name = oto.name,
                        birthday = oto.birthday
                    )
                }
            )

        }
    }

    override fun searchAllMembersByGroup(groupId: Long): MutableList<Person>? {
        return repository.findById(groupId).get().members
    }

    override fun saveMemberInGroup(member: RequestSaveMember) {
        logger.info("Salvando a pessoa: ${member.idPerson} no grupo: ${member.idGroup}")
        val group = repository.findById(member.idGroup)
        val person = personService.getPersonById(member.idPerson)
        if (!group.isEmpty) {
            group.get().members?.add(person)
        }

        invitationService.updateUsedGroupInvite(member.hash)
        repository.save(group.get())
    }

    override fun deleteMemberInGroup(idPerson: String, idGroup: String) {
        logger.info("Deletando a pessoa:$idPerson do grupo:$idGroup")
        val group = repository.findById(idGroup.toLong())
        if (group.isEmpty) {
            throw Exception("Grupo n??o existe!")
        }
        group.get().members?.remove(
            group.get().members!!.first { it.idPerson == idPerson.toLong() }
        )
        repository.save(group.get())
    }

}
