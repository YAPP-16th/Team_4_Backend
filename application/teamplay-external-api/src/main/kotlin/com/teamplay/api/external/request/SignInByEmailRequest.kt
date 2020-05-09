package com.teamplay.api.com.teamplay.api.external.request

import javax.validation.constraints.Email
import javax.validation.constraints.Size

class SignInByEmailRequest(
    @field:Email
    @field:Size(min = 1, max = 255)
    val email: String,
    @field:Size(min = 64, max = 64)
    val hashedPassword: String
)