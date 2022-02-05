package com.eh.niver.service.impl

import com.eh.niver.model.vo.EmailVO
import com.eh.niver.service.NotificationService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class NotificationServiceImpl(private val personService: PersonServiceImpl, private val emailService: EmailServiceImpl): NotificationService {

    companion object{
        private val logger = LoggerFactory.getLogger(NotificationServiceImpl::class.java)
    }

    override fun sendNotificationByPersonId(personId: Long) {
        logger.info("M=sendNotificationByPersonId msg=Enviando notificacao para a pessoa: $personId")
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

    override fun notificateBirthdaysToday(){
        logger.info("M=notificateBirthdaysToday msg=Buscando aniversariantes do dia ${LocalDate.now()}")
        val birthdays = personService.getBirthdaysToday()
        logger.info("Lista de aniversariantes: $birthdays")
        birthdays?.forEach {
            sendNotificationByPersonId(it.idPerson!!)
        }
    }

}