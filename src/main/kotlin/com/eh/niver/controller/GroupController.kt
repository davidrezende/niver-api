package com.eh.niver.controller

import com.eh.niver.model.Group
import com.eh.niver.model.vo.RequestSaveGroup
import com.eh.niver.model.vo.ResponseGroup
import com.eh.niver.model.vo.ResponseMember
import com.eh.niver.repository.GroupRepository
import com.eh.niver.repository.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("group/api")
class GroupController(val repository: GroupRepository, val repositoryPerson: PersonRepository) {

    companion object {
        private val logger = LoggerFactory.getLogger(PersonController::class.java)
    }

    @PostMapping()
    fun saveGroup(@RequestBody groupPostman: RequestSaveGroup): Group {
        logger.info("Salvando um Grupo: $groupPostman")
        val person = repositoryPerson.findByIdPerson(groupPostman.idOwner.toLong())
        if (person.isPresent) {
            logger.info("Pessoa encontrada pelo id: ${groupPostman.idOwner}")
            val parseGroup = Group(
                idGroup = null,
                name = groupPostman.name,
                owner = person.get()
            )
            return repository.save(parseGroup)
        } else {
            throw Exception("Pessoa no existe")
        }
    }

    @DeleteMapping("/{groupId}")
    fun deleteGroup(@PathVariable groupId: String) {
        logger.info("Deletando um Grupo: $groupId")
        return repository.deleteById(groupId.toLong())
    }

    @PostMapping("/member/{personId}/group/{groupId}")
    fun savePersonInGroup(@PathVariable personId: String, @PathVariable groupId: String) {
        logger.info("Inserindo um integrante com id:$personId ao grupo: $groupId")
        val group = repository.findById(groupId.toLong())
        val person = repositoryPerson.findByIdPerson(personId.toLong())
        if (!group.isEmpty && !person.isEmpty) {
            group.get().members?.add(person.get())
        }
        repository.save(group.get())
    }

    @DeleteMapping("/member/{personId}/group/{groupId}")
    fun deletePersonInGroup(@PathVariable personId: String, @PathVariable groupId: String) {
        logger.info("Deletando a pessoa:$personId do grupo:$groupId")
        val group = repository.findById(groupId.toLong())
        if (group.isEmpty) {
            throw Exception("Grupo não existe!")
        }
        group.get().members?.remove(
            group.get().members!!.first { it.idPerson == personId.toLong() }
        )
        repository.save(group.get())
    }

    @GetMapping("/members/groupOwner/{groupOwner}")
    fun searchGroupByOwnerWithMembers(@PathVariable groupOwner: Long): List<ResponseGroup> {
        val person = repositoryPerson.findByIdPerson(groupOwner)
        val groups = repository.findGroupByOwner(person)
        return groups.map {
            ResponseGroup(
                name = it.name,
                idGroup = it.idGroup,
                members = it.members?.map { oto ->
                    ResponseMember(
                        id = oto.idPerson,
                        name = oto.name
                    )
                }
            )

        }
    }

    @GetMapping("/group/{groupId}")
    fun searchGroupByOwnerWithMembers(@PathVariable groupId: String): ResponseGroup {
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
                    id = oto.idPerson,
                    name = oto.name
                )
            }
        )

    @PutMapping()
    fun updateGroup(@RequestBody groupPostman: RequestSaveGroup): Group {
        logger.info("Alterando um Grupo: $groupPostman")
        val person = repositoryPerson.findByIdPerson(groupPostman.idOwner.toLong())
        if (person.isPresent) {
            logger.info("Pessoa encontrada pelo id: ${groupPostman.idOwner}")
            val parseGroup = Group(
                idGroup = groupPostman.idGroup,
                name = groupPostman.name,
                owner = person.get()
            )
            return repository.save(parseGroup)
        } else {
            throw Exception("Pessoa no ecxiste")
        }

    }
}