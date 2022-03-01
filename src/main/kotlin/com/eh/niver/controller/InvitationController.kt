package com.eh.niver.controller

import com.eh.niver.model.vo.RequestCreateHash
import com.eh.niver.model.vo.ResponseGroupInvitation
import com.eh.niver.service.InvitationService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import java.util.*

@ApiOperation(value = "Endpoints de convite.")
@RestController
@RequestMapping("invite/api")
class InvitationController(val invitationService: InvitationService) {

    @ApiOperation(value = "Cria e altera um hash de convite para um grupo")
    @PostMapping()
    fun createHash(@RequestBody group: RequestCreateHash ): ResponseGroupInvitation?{
        return invitationService.createAndUpdateHash(group)
    }

    @ApiOperation(value = "Procura convite por grupo")
    @GetMapping("/{groupId}/{ownerId}")
    fun getInviteByGroupId(@PathVariable groupId: Long, @PathVariable ownerId: Long): ResponseGroupInvitation{
        println("grupo : $groupId e ownerId: $ownerId")
        return invitationService.getInviteByGroupId(groupId, ownerId)
    }

    @ApiOperation(value = "Procura grupo e owner por hash do convite")
    @GetMapping("/{hash}")
    fun getGroupByInvitationHash(@PathVariable hash: UUID): ResponseGroupInvitation{
        return invitationService.getGroupByInvitationHash(hash)
    }
}