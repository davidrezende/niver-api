package com.eh.niver.model.vo

data class ResponseGroup(
    var name: String,
    var idGroup: Long?,
    var members: List<ResponseMember>? = null,
    var owner: Long? = null
)