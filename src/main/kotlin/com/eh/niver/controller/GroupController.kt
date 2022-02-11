package com.eh.niver.controller

import com.eh.niver.model.Group
import com.eh.niver.model.vo.RequestSaveGroup
import com.eh.niver.model.vo.ResponseGroup
import com.eh.niver.service.GroupService
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@ApiOperation(value = "Endpoints de grupo.")
@RestController
@RequestMapping("group/api")
class GroupController(val groupService: GroupService) {

    companion object {
        private val logger = LoggerFactory.getLogger(GroupController::class.java)
    }

    @ApiOperation(value = "Salva um grupo.")
    @PostMapping()
    fun saveGroup(@RequestBody group: RequestSaveGroup): Group {
        return groupService.saveGroup(group)
    }

    @ApiOperation(value = "Deleta um grupo.")
    @DeleteMapping("/{groupId}")
    fun deleteGroup(@PathVariable groupId: String) {
        return groupService.deleteGroup(groupId)
    }

    @ApiOperation(value = "Busca grupo por id.")
    @GetMapping("/group/{groupId}")
    fun searchGroupById(@PathVariable groupId: String): ResponseGroup {
        return groupService.getGroupById(groupId)
    }

    @ApiOperation(value = "Atualiza um grupo.")
    @PutMapping()
    fun updateGroup(@RequestBody group: RequestSaveGroup): Group {
        return groupService.updateGroup(group)
    }
}

