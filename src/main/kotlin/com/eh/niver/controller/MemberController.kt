package com.eh.niver.controller

import com.eh.niver.model.vo.RequestSaveMember
import com.eh.niver.model.vo.ResponseGroup
import com.eh.niver.model.vo.ResponseMember
import com.eh.niver.repository.GroupRepository
import com.eh.niver.repository.PersonRepository
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@ApiOperation(value = "Endpoints de membro")
@RestController
@RequestMapping("member/api")
class MemberController(val repository: GroupRepository, val repositoryPerson: PersonRepository) {

    companion object {
        private val logger = LoggerFactory.getLogger(PersonController::class.java)
    }

    @ApiOperation(value = "Salva uma pessoa em um grupo.")
    @PostMapping()
    fun savePersonInGroup(@RequestBody member: RequestSaveMember) {
        logger.info("Inserindo um integrante com id:${member.idPerson} ao grupo: ${member.idGroup}")
        val group = repository.findById(member.idGroup.toLong())
        val person = repositoryPerson.findByIdPerson(member.idPerson.toLong())
        if (!group.isEmpty && !person.isEmpty) {
            group.get().members?.add(person.get())
        }
        repository.save(group.get())
    }

    @ApiOperation(value = "Deleta uma pessoa de um grupo.")
    @DeleteMapping("/deletePerson/{personId}/group/{groupId}")
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

    @ApiOperation(value = "Busca todos os grupos que uma pessoa é integrante.")
    @GetMapping("/searchGroup/person/{personId}")
    fun searchAllGroupsByPerson(@PathVariable personId: Long): List<ResponseGroup> {
        logger.info("Procurando grupos da pessoa: $personId")
        val person = repositoryPerson.findByIdPerson(personId)
        val groups = repository.findByMembers(person)
        return groups.map {
            ResponseGroup(
                name = it.name,
                idGroup = it.idGroup,
                owner = it.owner.idPerson,
                members = it.members?.map { oto ->
                    ResponseMember(
                        id = oto.idPerson,
                        name = oto.name
                    )
                }
            )

        }
    }
}

