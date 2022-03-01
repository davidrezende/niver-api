package com.eh.niver.model.vo

data class ResponseGroupInvitation(
    val groupId: Long,
    val groupName: String,
    val ownerId: Long?,
    val ownerName: String,
    val inviteHash: String? = null
)