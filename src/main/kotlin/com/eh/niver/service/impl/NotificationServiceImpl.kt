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
        val notificatedGroups = mutableListOf<Long?>()
        logger.info("Lista de aniversariantes: $birthdays")
        birthdays?.forEach {
            sendNotificationByPersonId(
                it.idPerson!!,
                "Feliz Aniversário ${it.name}",
                "${it.name}, hoje é o seu dia!\nNós da NiverDeQuem gostaríamos de te parabenizar por mais esse ano na sua vida.\nAss: NiverDeQuem <3 !"
            )
            logger.info("enviei email pro aniversariante ${it.name}")
            it.groups?.forEach { group ->
                if (group.members != null && group.members!!.isNotEmpty()) {
                    val nonBirthdayMembers = group.members?.filter { member -> !birthdays.contains(member) }
                    val birthdayMembers = group.members?.filter { member -> birthdays.contains(member) }

                    if (hasMoreThanOneBirthdayInGroup(nonBirthdayMembers!!, group.members!!)
                    ) {
                        if (!notificatedGroups.contains(group.idGroup)) {
                            nonBirthdayMembers.forEach { nonBirthdayMember ->
                                println("enviei email pra todos: ${it.name} com a lista $birthdayMembers")
                                sendNotificationByPersonId(
                                    nonBirthdayMember.idPerson!!,
                                    "Tem gente do ${group.name} fazendo aniversário hoje",
                                    "Olá, ${nonBirthdayMember.name}! Tudo bem?!\nVocê se lembra que do ${group.name}?! Então! Temos aniversariantes no grupo: \n${
                                        birthdayMembers?.map { birthday -> birthday.name }.toString()
                                            .replace("[", "")
                                            .replace("]", "")
                                    } \nAproveite para desejar seus parabéns o quanto antes!\nAss: NiverDeQuem <3"
                                )
                            }
                            notificatedGroups.add(group.idGroup)
                        }
                        logger.info("enviando em email para o aniversariante com a lista dos demais aniversariantes do dia ${birthdayMembers?.filter { birthday -> birthday.idPerson != it.idPerson!! }}")
                        sendNotificationByPersonId(
                            it.idPerson!!,
                            "Além de você o ${group.name} tem outros aniversariantes!",
                            "Olá, ${it.name}!\nSabemos que hoje é uma data especial pra você!\nAproveite para celebrar junto com os outros aniversariantes que também fazem parte do ${group.name}: \n${
                                birthdayMembers?.filter { birthday -> birthday.idPerson != it.idPerson!! }
                                    ?.map { birthday -> birthday.name }.toString()
                                    .replace("[", "")
                                    .replace("]", "")
                            } \nAss: NiverDeQuem <3"
                        )
                    } else {
                        nonBirthdayMembers.forEach { nonBirthdayMember ->
                            logger.info("enviei email apenas para o integrante: ${nonBirthdayMember.name} falando do aniversario do $it.idPerson!!")
                            sendNotificationByPersonId(
                                nonBirthdayMember.idPerson!!,
                                "${
                                    birthdayMembers?.get(0)?.name
                                } do ${group.name} faz aniversário hoje!",
                                "Olá, ${nonBirthdayMember.name}! Tudo bem?\nViemos lembrá-lo que hoje é aniversário do ${birthdayMembers?.get(0)?.name}.\nCelebrem essa data especial juntos!\nAss: NiverDeQuem <3"
                            )
                        }
                    }
                }
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
                        group.members!!.forEach { member ->
                            logger.info("enviei email pra todos: ${it.name} com a lista $birthdaysMonthly")
                            sendNotificationByPersonId(
                                member.idPerson!!,
                                "Tem gente do ${group.name} fazendo aniversário esse mês",
                                "Olá, ${member.name}! Tudo bem?!\nEstamos passando aqui para te lembrar dos aniversariantes do mês do ${group.name}:\n" +
                                        birthdayMembers?.map { birthday -> "\n" + birthday.name + " faz aniversário no dia " + birthday.birthday.dayOfMonth }
                                            .toString().replace("[", "").replace("]", "") + "\nAss: NiverDeQuem <3"
                            )
                        }
                        notificatedGroups.add(group.idGroup)
                    } else if (nonBirthdayMembers.isNotEmpty()) {
                        nonBirthdayMembers.forEach { nonBirthdayMember ->
                            logger.info("enviei email apenas para os integrantes: ${it.name}")
                            sendNotificationByPersonId(
                                nonBirthdayMember.idPerson!!,
                                "${birthdayMembers?.get(0)?.name} do ${group.name} faz aniversário esse mês!",
                                "Olá, ${nonBirthdayMember.name}! Tudo bem?!\nNão esquece, ${birthdayMembers?.get(0)?.name} está fazendo aniversário dia ${
                                    birthdayMembers?.get(
                                        0
                                    )?.birthday!!.dayOfMonth
                                } desse mês!\nJá prepara aquele presente especial, festa surpresa ou o que o seu coração mandar!\nAss: NiverDeQuem <3"
                            )
                        }
                    }
                }
            }
        }
    }
}
