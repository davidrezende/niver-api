package com.eh.niver.service

interface NotificationService {
    fun sendNotificationByPersonId(personId: Long)
    fun notificateBirthdaysToday()
    fun notificateMonthlyBirthdays();
}