package com.teamplay.domain.business.token.dto

data class AccessToken(
    val token: String,
    val expireIn: Long
)