package com.teamplay.api.com.teamplay.api.external.controller

import com.teamplay.api.com.teamplay.api.external.config.baseUrl
import com.teamplay.api.com.teamplay.api.external.response.UserClubsResponse
import com.teamplay.api.com.teamplay.api.external.service.AuthService
import com.teamplay.api.com.teamplay.api.external.service.ClubService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("$baseUrl/users")
class UserController {
    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var clubService: ClubService

    @ApiOperation(value = "가입한 클럽 모두 조회")
    @GetMapping("/user/clubs")
    @ResponseStatus(HttpStatus.OK)
    fun getClubs(@Valid @RequestHeader accessToken: String): UserClubsResponse {
        val user = authService.getUserByAccessToken(accessToken)

        return clubService.findUserClubs(user.id!!)
    }
}