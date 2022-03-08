package com.eh.niver.service.impl

import com.eh.niver.model.Group
import com.eh.niver.model.Person
import com.eh.niver.model.vo.EmailVO
import com.eh.niver.service.EmailService
import com.eh.niver.service.NotificationService
import com.eh.niver.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class NotificationServiceImpl(
    private val personService: PersonService,
    private val emailService: EmailService
) : NotificationService {

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationServiceImpl::class.java)
    }

    override fun sendNotificationByPersonId(personId: Long, subject: String, text: String) {
        logger.info("M=sendNotificationByPersonId msg=Enviando notificacao para a pessoa: $personId")
        val person = personService.getPersonById(personId)
        val message = EmailVO(
            to = person.email,
            name = person.name,
            subject = subject,
            text = text
        )
        return emailService.sendSimpleMessage(message)
    }

    override fun notificateBirthdaysToday() {
        logger.info("M=notificateBirthdaysToday msg=Buscando aniversariantes do dia ${LocalDate.now()}")
        val birthdays = personService.getBirthdaysToday()
        logger.info("Lista de aniversariantes: $birthdays")
        birthdays?.forEach {
            sendNotificationByPersonId(it.idPerson!!, "Feliz Aniversário ${it.name.split(" ").first()}", "Parabéns <3 !")
            println("enviei email pro aniversariante ${it.name}")
            it.groups?.forEach { group -> sendEmailToGroupOfBirthdayToday(birthdays, group, it.idPerson!!) }
        }
    }

    private fun sendEmailToGroupOfBirthdayToday(birthdays: List<Person>, group: Group, idPerson: Long) {
        val nonBirthdayMembers = group.members?.filter { member -> !birthdays.contains(member) }
        val birthdayMembers = group.members?.filter { member -> birthdays.contains(member) }

        if (hasMoreThanOneBirthdayInGroup(nonBirthdayMembers!!, group.members!!) ||
            nonBirthdayMembers.isEmpty()
        ) {
            nonBirthdayMembers.forEach {
                println("enviei email pra todos: ${it.name} com a lista $birthdayMembers")
                sendNotificationByPersonId(
                    it.idPerson!!,
                    "Tem gente do ${group.name} fazendo aniversário hoje",
                    "Aniversariantes do grupo: \n${birthdayMembers?.map { birthday ->  birthday.name }.toString().replace("[","").replace("]","")}"
                )
            }
            println("enviando em email para o aniversariante com a lista dos demais aniversariantes do dia ${birthdayMembers?.filter { birthday -> birthday.idPerson != idPerson }}")
            sendNotificationByPersonId(
                idPerson,
                "Tem gente do ${group.name} fazendo aniversário hoje também!",
                "Parabéns pelo seu aniversário!\nNessa data especial, aproveite para celebrar junto com os outros aniversariantes que fazem parte do mesmo grupo que você: \n${birthdayMembers?.filter { birthday -> birthday.idPerson != idPerson }?.map { birthday ->  birthday.name }.toString().replace("[","").replace("]","")}"
            )

        } else {
            nonBirthdayMembers.forEach {
                println("enviei email apenas para os integrantes: ${it.name} falando do aniversario do $idPerson")
                sendNotificationByPersonId(
                    it.idPerson!!,
                    "${birthdayMembers?.get(0)?.name?.split(" ")?.first()} do ${group.name} faz aniversario hoje!",
                    "${birthdayMembers?.get(0)?.name?.split(" ")?.first()} está fazendo aniversário hoje!"
                )
            }

        }

    }

    private fun hasMoreThanOneBirthdayInGroup(
        membrosQueNaoFazemNiverNoGrupo: List<Person>,
        membrosDoGrupo: List<Person>
    ): Boolean {
        return membrosQueNaoFazemNiverNoGrupo.isNotEmpty() && (membrosDoGrupo.size - membrosQueNaoFazemNiverNoGrupo.size) > 1
    }

    override fun notificateMonthlyBirthdays() {
        val birthdaysMonthly = personService.findByMonthlyBirthdays()
        val notificatedGroups = mutableListOf<Long?>()
        logger.info("Lista de aniversariantes do mês: $birthdaysMonthly")
        birthdaysMonthly?.forEach {
            it.groups?.forEach { group ->
                if (!notificatedGroups.contains(group.idGroup)) {
                    val nonBirthdayMembers = group.members?.filter { member -> !birthdaysMonthly.contains(member) }
                    val birthdayMembers = group.members?.filter { member -> birthdaysMonthly.contains(member) }

                    if (hasMoreThanOneBirthdayInGroup(nonBirthdayMembers!!, group.members!!)) {
                        group.members!!.forEach { members ->
                            println("enviei email pra todos: ${it.name} com a lista $birthdaysMonthly")
                            sendNotificationByPersonId(
                                members.idPerson!!,
                                "Tem gente do ${group.name} fazendo aniversário esse mês",
                                "Aniversariantes do mês: ${birthdayMembers?.map { birthday -> "\n" + birthday.name + " faz aniversário no dia " + birthday.birthday.dayOfMonth }.toString().replace("[","").replace("]","")}"
                            )
                        }
                        notificatedGroups.add(group.idGroup)
                    } else if (nonBirthdayMembers.isNotEmpty()) {
                        nonBirthdayMembers.forEach { nonBirthdayMember -> println("enviei email apenas para os integrantes: ${it.name}")
                            sendNotificationByPersonId(
                                nonBirthdayMember.idPerson!!,
                                "${birthdayMembers?.get(0)?.name?.split(" ")?.first()} do ${group.name} faz aniversário esse mês!",
                                "${birthdayMembers?.get(0)?.name?.split(" ")?.first()} está fazendo aniversário esse mês!")
                        }
                    }

                }

            }
        }
    }

}
