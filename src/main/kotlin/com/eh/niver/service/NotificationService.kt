package com.eh.niver.service

import com.eh.niver.model.enum.EmailTypeEnum

interface NotificationService {
    fun sendNotificationByPersonId(personId: Long, type: EmailTypeEnum)
    fun notificateBirthdaysToday(daysToBirthday: Int)
}