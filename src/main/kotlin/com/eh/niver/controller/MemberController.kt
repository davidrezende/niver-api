package com.eh.niver.controller

import com.eh.niver.model.vo.RequestSaveMember
import com.eh.niver.model.vo.ResponseGroup
import com.eh.niver.service.MemberService
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@ApiOperation(value = "Endpoints de membro")
@RestController
@RequestMapping("member/api")
class MemberController(val memberService: MemberService) {

    companion object {
        private val logger = LoggerFactory.getLogger(MemberController::class.java)
    }

    @ApiOperation(value = "Salva uma pessoa em um grupo com um convite.")
    @PostMapping()
    fun savePersonInGroup(@RequestBody member: RequestSaveMember) {
        memberService.saveMemberInGroup(member)
    }

    @CrossOrigin
    @ApiOperation(value = "Deleta uma pessoa de um grupo.")
    @DeleteMapping("/deletePerson/{personId}/group/{groupId}")
    fun deletePersonInGroup(@PathVariable personId: String, @PathVariable groupId: String) {
        memberService.deleteMemberInGroup(personId, groupId)
    }

    @CrossOrigin
    @ApiOperation(value = "Busca todos os grupos que uma pessoa é integrante.")
    @GetMapping("/searchGroup/person/{personId}")
    fun searchAllGroupsByPerson(@PathVariable personId: Long): List<ResponseGroup> {
        return memberService.searchAllGroupsByMember(personId)
    }
}

