package com.eh.niver.service.impl

import com.eh.niver.model.vo.ResponseCalendarBirthdays
import com.eh.niver.service.CalendarService
import com.eh.niver.service.PersonService
import org.springframework.stereotype.Service

@Service
class CalendarServiceImpl(val personService: PersonService) : CalendarService {
    override fun getBirthdaysFromAllMemberOfAllGroups(month: Int, idPerson: Long): MutableList<ResponseCalendarBirthdays> {
        val person = personService.getPersonById(idPerson)
        val listBirthdays: MutableList<ResponseCalendarBirthdays> = emptyList<ResponseCalendarBirthdays>().toMutableList()
        person.groups?.forEach { group ->
            group.members?.filter { member -> member.birthday.month.value == month }?.forEach { memberBirthdayMonthly ->
                listBirthdays.add(
                    ResponseCalendarBirthdays(
                        idPerson = memberBirthdayMonthly.idPerson!!,
                        name = memberBirthdayMonthly.name,
                        day = memberBirthdayMonthly.birthday.dayOfMonth
                    )
                )
            }
        }
        return listBirthdays.distinct().toMutableList()
    }
}