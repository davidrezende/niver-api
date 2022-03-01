package com.eh.niver.service

import com.eh.niver.model.vo.RequestCreateHash
import com.eh.niver.model.vo.ResponseGroupInvitation
import java.util.*

interface InvitationService {
    fun createAndUpdateHash(group: RequestCreateHash): ResponseGroupInvitation
    fun getInviteByGroupId(groupId: Long, ownerId: Long): ResponseGroupInvitation
    fun getGroupByInvitationHash(hash: UUID): ResponseGroupInvitation
    fun updateUsedGroupInvite(hash: UUID)
}