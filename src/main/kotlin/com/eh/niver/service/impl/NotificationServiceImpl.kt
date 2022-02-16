package com.eh.niver.service.impl

import com.eh.niver.model.vo.EmailVO
import com.eh.niver.service.EmailService
import com.eh.niver.service.GroupService
import com.eh.niver.service.NotificationService
import com.eh.niver.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class NotificationServiceImpl(
    private val personService: PersonService,
    private val emailService: EmailService,
    private val groupService: GroupService
) : NotificationService {

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationServiceImpl::class.java)
    }

    override fun sendNotificationByPersonId(personId: Long) {
        logger.info("M=sendNotificationByPersonId msg=Enviando notificacao para a pessoa: $personId")
        val person = personService.getPersonById(personId)
        val message = EmailVO(
            to = person.email,
            name = person.name
        )
        return emailService.sendSimpleMessage(message)
    }

    override fun notificateBirthdaysToday() {
        logger.info("M=notificateBirthdaysToday msg=Buscando aniversariantes do dia ${LocalDate.now()}")
        val birthdays = personService.getBirthdaysToday()
        logger.info("Lista de aniversariantes: $birthdays")
        birthdays?.forEach {
            sendNotificationByPersonId(it.idPerson!!)
            it.groups?.forEach { sendEmailToGroup(it.idGroup!!) }
        }
    }

    override fun sendEmailToGroup(groupId: Long) {
        val birthdays = personService.getBirthdaysToday()
        val members = groupService.getGroupById(groupId.toString()).members
        members?.filterNot { birthdays?.map { it.idPerson } == listOf(it.idPerson) }?.forEach {
            sendNotificationByPersonId(it.idPerson)
        }
    }

}