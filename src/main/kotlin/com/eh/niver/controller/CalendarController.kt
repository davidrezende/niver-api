package com.eh.niver.controller

import com.eh.niver.model.vo.ResponseCalendarBirthdays
import com.eh.niver.service.CalendarService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Calendario")
@SecurityRequirement(name = "niverapi")
@RequestMapping("calendar/api")
@RestController
class CalendarController(val calendarService: CalendarService) {

    @Operation(summary = "Endpoint para buscar aniversariantes do mês de todos os grupos de uma pessoa")
    @GetMapping("/birthdays/{month}/{idPerson}")
    fun getDateBirthdaysFromAllMemberOfAllGroups(@PathVariable month: Int,
                                                 @PathVariable idPerson: Long) : MutableList<ResponseCalendarBirthdays> {
        return calendarService.getBirthdaysFromAllMemberOfAllGroups(month, idPerson)
    }

}