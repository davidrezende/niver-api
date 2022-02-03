package com.eh.niver.model.vo

import com.fasterxml.jackson.annotation.JsonIgnore

data class ResponseGroup(
    var name: String,
    var idGroup: Long?,
    var members: List<ResponseMember>? = null,
    @JsonIgnore
    var owner: Long? = null
)