package com.eh.niver.model.vo

data class RequestUpdatePasswordPerson(
    val idPerson: Long,
    val password: String,
    val newPassword: String
)