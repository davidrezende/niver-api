package com.eh.niver.service

interface NotificationService {
    fun sendNotificationByPersonId(personId: Long, subject: String, text: String)
    fun notificateBirthdaysToday()
    fun notificateMonthlyBirthdays();
}