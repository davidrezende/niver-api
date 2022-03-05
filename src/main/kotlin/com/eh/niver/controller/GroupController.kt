package com.eh.niver.controller

import com.eh.niver.model.Group
import com.eh.niver.model.vo.RequestSaveGroup
import com.eh.niver.model.vo.ResponseGroup
import com.eh.niver.service.GroupService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@Tag(name = "Grupo")
@SecurityRequirement(name = "niverapi")
@RestController
@RequestMapping("group/api")
class GroupController(val groupService: GroupService) {

    companion object {
        private val logger = LoggerFactory.getLogger(GroupController::class.java)
    }

    @Operation(summary = "Salva um grupo.")
    @PostMapping
    fun saveGroup(@RequestBody group: RequestSaveGroup): Group {
        return groupService.saveGroup(group)
    }

    @Operation(summary = "Deleta um grupo.")
    @DeleteMapping("/{groupId}")
    fun deleteGroup(@PathVariable groupId: String) {
        return groupService.deleteGroup(groupId)
    }

    @Operation(summary = "Busca grupo por id.")
    @GetMapping("/group/{groupId}")
    fun searchGroupById(@PathVariable groupId: String): ResponseGroup {
        return groupService.getGroupById(groupId)
    }

    @Operation(summary = "Atualiza um grupo.")
    @PutMapping()
    fun updateGroup(@RequestBody group: RequestSaveGroup): Group {
        return groupService.updateGroup(group)
    }
}

