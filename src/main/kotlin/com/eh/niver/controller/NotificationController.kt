package com.eh.niver.controller

import com.eh.niver.model.vo.RequestSendEmail
import com.eh.niver.service.GroupService
import com.eh.niver.service.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Notificacoes")
@RestController
@RequestMapping("notification/api")
class NotificationController(
    val notificationService: NotificationService,
    val groupService: GroupService
) {

    @Operation(summary = "Envia um email de teste.")
    @PostMapping("/send")
    fun sendEmail(@RequestBody request: RequestSendEmail) {
        try {
            notificationService.sendNotificationByPersonId(request.idPerson, "null", "null")
        } catch (e: Exception) {
            throw e
        }
    }

    @Operation(summary =  "Envia um email de teste para aniversariantes.")
    @GetMapping("/send/birthday")
    fun sendEmailToGroupTeste() {
        try {
            notificationService.notificateBirthdaysToday()
        } catch (e: Exception) {
            throw e
        }
    }
}