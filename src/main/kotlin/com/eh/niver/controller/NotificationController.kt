package com.eh.niver.controller

import com.eh.niver.model.vo.RequestSendEmail
import com.eh.niver.service.GroupService
import com.eh.niver.service.NotificationService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@Api(value = "Endpoints de notificacoes.")
@RestController
@RequestMapping("notification/api")
class NotificationController(
    val notificationService: NotificationService,
    val groupService: GroupService
) {

    @ApiOperation(value = "Envia um email de teste.")
    @PostMapping("/send")
    fun sendEmail(@RequestBody request: RequestSendEmail) {
        try {
            notificationService.sendNotificationByPersonId(request.idPerson, "null", "null")
        } catch (e: Exception) {
            throw e
        }
    }

    @ApiOperation(value = "Envia um email de teste para aniversariantes.")
    @GetMapping("/send/birthday")
    fun sendEmailToGroupTeste() {
        try {
            notificationService.notificateBirthdaysToday()
        } catch (e: Exception) {
            throw e
        }
    }
}