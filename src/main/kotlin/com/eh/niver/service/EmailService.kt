package com.eh.niver.service

import com.eh.niver.model.vo.EmailVO
import org.springframework.mail.SimpleMailMessage

interface EmailService {
    fun sendEmail(message: SimpleMailMessage)
    fun sendSimpleMessage(data: EmailVO)
}