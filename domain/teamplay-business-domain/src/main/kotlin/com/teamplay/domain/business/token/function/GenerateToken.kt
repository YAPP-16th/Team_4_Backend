package com.teamplay.domain.business.token.function

import com.teamplay.core.function.Function
import com.teamplay.core.token.Token
import io.jsonwebtoken.Claims

abstract class GenerateToken(
    private val defaultClaims: Claims
): Function<Claims, Token> {
    override fun apply(claims: Claims): Token {
        claims.putAll(defaultClaims)

        return Token.from(claims)
    }
}