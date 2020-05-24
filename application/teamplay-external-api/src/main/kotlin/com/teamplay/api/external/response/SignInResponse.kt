package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.token.dto.AccessToken
import com.teamplay.domain.business.user.dto.UserInfo

data class SignInResponse(
    val accessToken: AccessToken,
    val refreshToken: String,
    val userInfo: UserInfo,
    override val message: String? = "로그인 정보"
): Response()