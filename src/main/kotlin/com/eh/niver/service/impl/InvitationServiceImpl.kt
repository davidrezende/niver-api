package com.eh.niver.service.impl

import com.eh.niver.model.Invitation
import com.eh.niver.model.vo.*
import com.eh.niver.repository.GroupRepository
import com.eh.niver.repository.InvitationRepository
import com.eh.niver.service.InvitationService
import com.eh.niver.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import javax.transaction.Transactional

@Service
class InvitationServiceImpl(
    val repository: InvitationRepository,
    val personService: PersonService,
    val groupRepository: GroupRepository
) : InvitationService {

    companion object {
        private val logger = LoggerFactory.getLogger(InvitationServiceImpl::class.java)
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun createAndUpdateHash(group: RequestCreateHash): UUID {
        val grupo = groupRepository.findById(group.idGroup)
        if (group.owner != grupo.get().owner.idPerson) {
            throw Exception("O dono do grupo não é o mesmo.")
        }
        if (grupo.get().invite == null) {
            logger.info("M=createAndUpdateHash msg= Convite inexistente, criando um novo.")
            val inv = repository.save(
                Invitation(
                    used = 0,
                    creation = LocalDate.now(),
                    group = grupo.get()
                )
            )
            return inv.uuidHash
        } else {
            logger.info("M=createAndUpdateHash msg= Convite ja existe, deletando e criando um novo.")
            repository.deleteById(grupo.get().invite!!.uuidHash)
            val inv = repository.save(
                Invitation(
                    used = 0,
                    creation = LocalDate.now(),
                    group = grupo.get()
                )
            )
            return inv.uuidHash
        }
    }

    override fun getInviteByGroupId(groupId: String, ownerId: String): UUID {
        val grupo = groupRepository.findById(groupId.toLong())
        logger.info("M=getInviteByGroupId msg=Buscando hash pelo pelo grupo: $groupId")
        if (ownerId.toLong() != grupo.get().owner.idPerson) {
            throw Exception("O dono do grupo não é o mesmo.")
        }
        val inv = repository.findInvitationByGroup(groupRepository.findById(groupId.toLong()))

        return inv.uuidHash
    }

    override fun getGroupByInvitationHash(hash: UUID): ResponseGroupInvitation {
        val grupo = repository.findById(hash).get().group
        logger.info("M=getGroupByInvitationHash msg=Buscando grupo pelo pela hash: $hash")
        return ResponseGroupInvitation(
            groupId = grupo.idGroup!!.toLong(),
            groupName = grupo.name,
            ownerId = grupo.owner.idPerson,
            ownerName = grupo.owner.name
        )
    }

    override fun updateUsedGroupInvite(hash: UUID) {
        val inv = repository.getById(hash)
        inv.apply {
            used = used?.plus(1)
        }
        logger.info("M=updateUsedGroupInvite msg=: Alterando convites usados do grupo para: ${inv.used}")
        repository.save(
            Invitation(
                uuidHash = inv.uuidHash,
                used = inv.used,
                group = groupRepository.getById(inv.group.idGroup!!),
                creation = inv.creation
            )
        )
    }
}