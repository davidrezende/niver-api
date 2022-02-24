package com.eh.niver.controller

import com.eh.niver.model.vo.ResponseCalendarBirthdays
import com.eh.niver.service.CalendarService
import org.springframework.web.bind.annotation.*

@RequestMapping("calendar/api")
@RestController
class CalendarController(val calendarService: CalendarService) {

    @GetMapping("/birthdays/{month}/{idPerson}")
    fun getDateBirthdaysFromAllMemberOfAllGroups(@PathVariable month: Int,
                                                 @PathVariable idPerson: Long) : MutableList<ResponseCalendarBirthdays> {
        return calendarService.getBirthdaysFromAllMemberOfAllGroups(month, idPerson)
    }

}