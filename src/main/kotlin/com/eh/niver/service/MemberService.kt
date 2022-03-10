package com.eh.niver.service

import com.eh.niver.model.vo.RequestSaveMember
import com.eh.niver.model.vo.ResponseGroup

interface MemberService {
    fun saveMemberInGroup(member: RequestSaveMember)
    fun deleteMemberInGroup(idPerson: String, idGroup: String)
    fun searchAllGroupsByMember(personId: Long): List<ResponseGroup>
}