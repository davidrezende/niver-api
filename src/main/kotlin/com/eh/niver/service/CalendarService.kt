package com.eh.niver.service

import com.eh.niver.model.vo.ResponseCalendarBirthdays
import com.eh.niver.model.vo.ResponseMember

interface CalendarService {
    fun getBirthdaysFromAllMemberOfAllGroups(month: Int, idPerson: Long) : MutableList<ResponseCalendarBirthdays>
}