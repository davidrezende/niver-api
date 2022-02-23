package com.eh.niver.model.vo

import java.time.LocalDate

data class RequestUpdatePerson(
    val idPerson: Long,
    val name: String,
    val email: String,
    val birthday: LocalDate,
    val confirmPassword: String
)