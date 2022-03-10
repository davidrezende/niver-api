package com.eh.niver.controller

import com.eh.niver.model.vo.RequestCreateHash
import com.eh.niver.model.vo.ResponseGroupInvitation
import com.eh.niver.service.InvitationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "Convite")
@RestController
@RequestMapping("invite/api")
class InvitationController(val invitationService: InvitationService) {

    @SecurityRequirement(name = "niverapi")
    @Operation(summary = "Cria e altera um hash de convite para um grupo")
    @PostMapping()
    fun createHash(@RequestBody group: RequestCreateHash): ResponseGroupInvitation? {
        return invitationService.createAndUpdateHash(group)
    }

    @SecurityRequirement(name = "niverapi")
    @Operation(summary = "Procura convite por grupo")
    @GetMapping("/{groupId}/{ownerId}")
    fun getInviteByGroupId(@PathVariable groupId: Long, @PathVariable ownerId: Long): ResponseGroupInvitation {
        println("grupo : $groupId e ownerId: $ownerId")
        return invitationService.getInviteByGroupId(groupId, ownerId)
    }

    @Operation(summary = "Procura grupo e owner por hash do convite")
    @GetMapping("info/{hash}")
    fun getGroupByInvitationHash(@PathVariable hash: UUID): ResponseGroupInvitation {
        return invitationService.getGroupByInvitationHash(hash)
    }
}