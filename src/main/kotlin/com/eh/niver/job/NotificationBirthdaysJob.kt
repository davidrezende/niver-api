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
    @Scheduled(cron = "0 45 18 * * *")
    @Transactional
    fun notificationBirthdays() {
        logger.info("M=notificationBirthdays msg=init at ${LocalDateTime.now()}")
        notificationService.notificateBirthdaysToday()
        logger.info("M=notificationBirthdays msg=end at ${LocalDateTime.now()}")
    }

    @Async
    @Scheduled(cron = "0 45 18 * * *")
    @Transactional
    fun notificationMonthlyBirthdays(){
        logger.info("M=notificationMonthlyBirthdays msg=init at ${LocalDateTime.now()}")
        notificationService.notificateMonthlyBirthdays()
        logger.info("M=notificationMonthlyBirthdays msg=end at ${LocalDateTime.now()}")
    }

}
//cron = "0 26 19 21 1-12 *"