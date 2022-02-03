package com.eh.niver.service.impl

import com.eh.niver.model.vo.EmailVO
import com.eh.niver.service.NotificationService
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(private val personService: PersonServiceImpl, private val emailService: EmailServiceImpl): NotificationService {
    override fun sendNotificationByPersonId(personId: Long) {
        val person = personService.getPersonById(personId)
        if(person.isPresent) {
            val message = EmailVO(
                to = person.get().email,
                name = person.get().name
            )
            return emailService.sendSimpleMessage(message)
        }
        throw Exception("Dados da pessoa não encontrados para enviar o email")
    }

}