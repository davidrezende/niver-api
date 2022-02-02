package com.eh.niver.model.vo

import com.eh.niver.model.Person

data class ResponseGroup(
    var name: String,
    var idGroup: Long?,
    var people: List<ResponseMembers>? = null
)