package com.eh.niver.service.impl

import com.eh.niver.model.vo.RequestSaveMember
import com.eh.niver.service.GroupService
import com.eh.niver.service.MemberService
import com.eh.niver.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(val personService: PersonService, val groupService: GroupService) : MemberService {
    companion object {
        private val logger = LoggerFactory.getLogger(MemberServiceImpl::class.java)
    }

    override fun savePersonInGroup(member: RequestSaveMember) {
        groupService.saveMemberInGroup(member)
    }


}