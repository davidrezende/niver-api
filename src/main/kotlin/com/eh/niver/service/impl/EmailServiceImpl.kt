package com.eh.niver.service.impl

import com.eh.niver.model.vo.EmailVO
import com.eh.niver.service.EmailService
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(val emailSender: JavaMailSender): EmailService {
    override fun sendEmail(message: SimpleMailMessage) {
        emailSender.send(message)
    }

    override fun sendSimpleMessage(data: EmailVO) {
        val message = SimpleMailMessage()
        message.setFrom(data.from)
        message.setTo(data.to)
        message.setSubject(data.subject)
        message.setText(data.text)
        sendEmail(message)
    }
}