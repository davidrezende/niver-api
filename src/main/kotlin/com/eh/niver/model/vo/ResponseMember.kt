package com.eh.niver.model.vo

import java.time.LocalDate

data class ResponseMember(
    var idPerson : Long,
    var name: String?,
    var birthday: LocalDate? = null
)