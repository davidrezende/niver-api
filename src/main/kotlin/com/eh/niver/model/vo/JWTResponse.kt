package com.eh.niver.model.vo

data class JWTResponse(
    val accessToken: String,
    val userId: Long,
    val userName: String
)