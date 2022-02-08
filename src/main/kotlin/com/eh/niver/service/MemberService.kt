package com.eh.niver.service

import com.eh.niver.model.vo.RequestSaveMember

interface MemberService {
    fun savePersonInGroup(member: RequestSaveMember)
}