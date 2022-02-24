package com.eh.niver.controller

import com.eh.niver.model.Invitation
import com.eh.niver.model.vo.RequestCreateHash
import com.eh.niver.service.InvitationService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@ApiOperation(value = "Endpoints de convite.")
@RestController
@RequestMapping("invite/api")
class InvitationController(val invitationService: InvitationService) {

    @ApiOperation(value = "Salva uma pessoa.")
    @PostMapping()
    fun createHash(@RequestBody group: RequestCreateHash ): Invitation{
        return invitationService.createHash(group)
    }


}