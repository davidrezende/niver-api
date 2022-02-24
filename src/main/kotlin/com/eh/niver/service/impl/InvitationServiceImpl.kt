package com.eh.niver.service.impl

import com.eh.niver.model.Invitation
import com.eh.niver.model.vo.RequestCreateHash
import com.eh.niver.repository.GroupRepository
import com.eh.niver.repository.InvitationRepository
import com.eh.niver.service.InvitationService
import com.eh.niver.service.PersonService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class InvitationServiceImpl(val repository: InvitationRepository, val personService: PersonService, val groupRepository: GroupRepository):InvitationService{
    override fun createHash(group: RequestCreateHash): Invitation{
        val grupo = groupRepository.findById(group.idGroup)
        return repository.save(Invitation(
            used = 0,
            creation = LocalDate.now(),
            group = grupo.get()
        ))
    }
}