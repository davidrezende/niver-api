package com.eh.niver.model

import java.time.LocalDate

data class Person(
    var idPerson: Long,
    var name: String,
    var birthday: LocalDate,
    var email: String,
    var password: String
)