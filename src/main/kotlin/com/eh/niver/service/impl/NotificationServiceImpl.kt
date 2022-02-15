package com.eh.niver.service.impl

import com.eh.niver.model.vo.EmailVO
import com.eh.niver.repository.PersonRepository
import com.eh.niver.service.NotificationService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class NotificationServiceImpl(
    private val personService: PersonServiceImpl,
    private val emailService: EmailServiceImpl,
    private val personRepository: PersonRepository
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
            println(it.groups)
//            sendNotificationByPersonId(it.idPerson!!)
        }
    }

    override fun notificateMonthlyBirthdays() {
        //colocar camada de service de person
        val birthdaysMonthly = personRepository.findByMonthlyBirthdays()
        birthdaysMonthly?.forEach {
            println(it.name)
        }
    }

}