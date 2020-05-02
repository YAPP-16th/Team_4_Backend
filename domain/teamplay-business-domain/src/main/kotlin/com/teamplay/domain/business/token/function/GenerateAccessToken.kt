package com.teamplay.domain.business.token.function

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.util.*

class GenerateAccessToken(
    private val defaultClams: Claims = Jwts.claims(),
    private val expirationInMs: Int
): GenerateToken(
    Jwts.claims().apply {
        putAll(defaultClams)
        subject = "AccessToken"
        expiration = Date(Date().time + expirationInMs)
    }
)