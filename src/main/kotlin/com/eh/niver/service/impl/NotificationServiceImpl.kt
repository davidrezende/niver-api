package com.eh.niver.service.impl

import com.eh.niver.model.Person
import com.eh.niver.model.enum.EmailTypeEnum
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

    override fun sendNotificationByPersonId(personId: Long, type: EmailTypeEnum) {
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

    /*
    Notificar aniversariantes do DIA, SEMANAL MENSAL
    Notificar membros que façam parte do mesmo grupo dos aniversariantes
        -> Caso o mesmo grupo contenha > 1 aniversariante no dia:
            -> Notifica os aniversariantes individualmente e os membros do grupo
             com apenas um email com a lista dos aniversariantes do dia
            -> Dias anteriores:
                -> Notifica o grupo com a lista de aniversariantes, incluindo os aniversariantes
        -> Caso o grupo só tenha um aniversariante no range
            -> No dia do aniversário:
                -> Notifica o aniversariante e os membros dos grupos que ele fizer parte
            -> Dias anteriores:
                -> Não notifica o próprio aniversariante e notifica os membros dos grupos que o aniversariante do range fizer parte
     */
    override fun notificateBirthdaysToday(daysToBirthday: Int){
        logger.info("M=notificateBirthdaysToday msg=Buscando aniversariantes do dia ${LocalDate.now()}")
        val birthdays = personService.getBirthdaysToday()
        logger.info("Lista de aniversariantes: $birthdays")
        birthdays?.forEach { birthdayPerson ->
            sendNotificationByPersonId(birthdayPerson.idPerson!!, EmailTypeEnum.BIRTHDAY_PERSON)
            birthdayPerson.groups?.forEach { groups ->
                groups.members?.forEach { groupMember ->
                    sendNotificationByPersonId(groupMember.idPerson!!, EmailTypeEnum.GROUP_WITH_BIRTHDAY_PERSON)
                }
            }
        }

    }

}