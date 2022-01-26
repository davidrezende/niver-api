package com.eh.niver.controller

import com.eh.niver.model.Group
import com.eh.niver.model.vo.RequestSaveGroup
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
            throw Exception("Pessoa no ecxiste")
        }
    }

    @DeleteMapping("/{groupId}")
    fun deleteGroup(@PathVariable groupId: String) {
        logger.info("Deletenado um Grupo: $groupId")
        return repository.deleteById(groupId.toLong())
    }

    @GetMapping("/GroupOwner/{groupOwner}")
    fun searchGroupByOwner(@PathVariable groupOwner: Long): List<Group>{
        val person = repositoryPerson.findByIdPerson(groupOwner)
        return repository.findGroupByOwner(person)
    }
}