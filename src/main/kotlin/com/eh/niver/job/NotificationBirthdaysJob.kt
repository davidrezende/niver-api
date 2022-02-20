package com.eh.niver.job

import com.eh.niver.service.NotificationService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
@EnableAsync
class NotificationBirthdaysJob(val notificationService: NotificationService) {

    companion object{
        private val logger = LoggerFactory.getLogger(NotificationBirthdaysJob::class.java)
    }

    @Async
//    @Scheduled(fixedRate = 60000, initialDelay = 5000)
    @Transactional
    fun notificationBirthdays() {
        logger.info("M=notificationBirthdays msg=init at ${LocalDateTime.now()}")
        notificationService.notificateBirthdaysToday()
        logger.info("M=notificationBirthdays msg=end at ${LocalDateTime.now()}")
    }
}