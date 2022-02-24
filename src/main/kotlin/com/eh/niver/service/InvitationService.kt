package com.eh.niver.service

import com.eh.niver.model.Invitation
import com.eh.niver.model.vo.RequestCreateHash

interface InvitationService {
    fun createHash(group: RequestCreateHash): Invitation
}