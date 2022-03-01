package com.eh.niver.repository

import com.eh.niver.model.Group
import com.eh.niver.model.Invitation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface InvitationRepository: JpaRepository<Invitation,UUID>{
    fun findInvitationByGroup(group: Optional<Group>): Invitation
}