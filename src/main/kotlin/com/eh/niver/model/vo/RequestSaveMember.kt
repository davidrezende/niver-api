package com.eh.niver.model.vo

import java.util.*

data class RequestSaveMember(
    val idPerson : Long,
    val idGroup : Long,
    val hash: UUID
)