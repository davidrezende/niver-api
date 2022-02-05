package com.eh.niver.controller

import com.eh.niver.model.Group
import com.eh.niver.model.vo.RequestSaveGroup
import com.eh.niver.model.vo.ResponseGroup
import com.eh.niver.model.vo.ResponseMember
import com.eh.niver.repository.GroupRepository
import com.eh.niver.repository.PersonRepository
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@ApiOperation(value = "Endpoints de grupo.")
@RestController
@RequestMapping("group/api")
class GroupController(val repository: GroupRepository, val repositoryPerson: PersonRepository) {

    companion object {
        private val logger = LoggerFactory.getLogger(PersonController::class.java)
    }

    @ApiOperation(value = "Salva um grupo.")
    @PostMapping()
    fun saveGroup(@RequestBody groupPostman: RequestSaveGroup): Group {
        logger.info("Salvando um Grupo: $groupPostman")
        val person = repositoryPerson.findByIdPerson(groupPostman.idOwner.toLong())
        if (person.isPresent) {
            logger.info("Pessoa encontrada pelo id: ${groupPostman.idOwner}")
            val parseGroup = Group(
                idGroup = null,
                name = groupPostman.name,
                owner = person.get(),
                members = mutableListOf(person.get())
            )
            return repository.save(parseGroup)
        } else {
            throw Exception("Pessoa no existe")
        }
    }

    @ApiOperation(value = "Deleta um grupo.")
    @DeleteMapping("/{groupId}")
    fun deleteGroup(@PathVariable groupId: String) {
        logger.info("Deletando um Grupo: $groupId")
        return repository.deleteById(groupId.toLong())
    }

    @ApiOperation(value = "Busca grupo por id.")
    @GetMapping("/group/{groupId}")
    fun searchGroupById(@PathVariable groupId: String): ResponseGroup {
        logger.info("Procurando o grupo : $groupId")
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
    }

    @ApiOperation(value = "Atualiza um grupo.")
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

