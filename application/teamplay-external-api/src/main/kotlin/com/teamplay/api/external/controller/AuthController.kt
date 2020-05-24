package com.teamplay.api.com.teamplay.api.external.controller

import com.teamplay.api.com.teamplay.api.external.config.baseUrl
import com.teamplay.api.com.teamplay.api.external.request.SignInByEmailRequest
import com.teamplay.api.com.teamplay.api.external.request.SignUpByEmailRequest
import com.teamplay.api.com.teamplay.api.external.response.PossibleEmailResponse
import com.teamplay.api.com.teamplay.api.external.response.PossibleNicknameResponse
import com.teamplay.api.com.teamplay.api.external.response.SignInResponse
import com.teamplay.api.com.teamplay.api.external.service.AuthService
import com.teamplay.domain.business.token.dto.AccessToken
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("$baseUrl/auth")
class AuthController {
    @Autowired
    private lateinit var authService: AuthService

    @ApiOperation(value = "로그인")
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@RequestBody signInByEmailRequest: SignInByEmailRequest): SignInResponse {
        return authService.signInByEmail(signInByEmailRequest)
    }

    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody signUpByEmailRequest: SignUpByEmailRequest): SignInResponse {
        return authService.signUpByEmail(signUpByEmailRequest)
    }

    @ApiOperation(value = "이메일 중복 체크")
    @GetMapping("/exist/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun checkDuplicateUserEmail(@PathVariable email: String): PossibleEmailResponse {
        return authService.checkPossibleUserEmail(email)
    }

    @ApiOperation(value = "닉네임 중복 체크")
    @GetMapping("/exist/nickname/{nickname}")
    @ResponseStatus(HttpStatus.OK)
    fun checkDuplicateUserNickname(@PathVariable nickname: String): PossibleNicknameResponse {
        return authService.checkPossibleUserNickname(nickname)
    }

    @ApiOperation(value = "액세스 토큰 재발급")
    @GetMapping("/access-token")
    @ResponseStatus(HttpStatus.OK)
    fun refreshAccessToken(@Valid @RequestHeader refreshToken: String): AccessToken {
        return authService.refreshAccessToken(refreshToken)
    }
}