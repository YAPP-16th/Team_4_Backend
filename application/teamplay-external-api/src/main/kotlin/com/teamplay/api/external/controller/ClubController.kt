package com.teamplay.api.com.teamplay.api.external.controller

import com.teamplay.api.com.teamplay.api.external.request.CreateClubRequest
import com.teamplay.api.com.teamplay.api.external.request.GetClubsRequest
import com.teamplay.api.com.teamplay.api.external.response.ClubResponse
import com.teamplay.api.com.teamplay.api.external.response.ClubsResponse
import com.teamplay.api.com.teamplay.api.external.response.CreateClubResponse
import com.teamplay.api.com.teamplay.api.external.service.AuthService
import com.teamplay.api.com.teamplay.api.external.service.ClubService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/clubs")
class ClubController {
    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var clubService: ClubService

    @ApiOperation(value = "동호회 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerClub(
        @Valid @RequestHeader(required = false) accessToken: String,
        @RequestBody createClubRequest: CreateClubRequest
    ): CreateClubResponse {
        val user = authService.getUserByAccessToken(accessToken)
        return clubService.registerClub(createClubRequest, user)
    }

    @ApiOperation(value = "동호회 목록 정보")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getClubs(getClubsRequest: GetClubsRequest): ClubsResponse {

        return clubService.findClubInfosByName(getClubsRequest)
    }

    @ApiOperation(value = "동호회 정보")
    @GetMapping("/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    fun getClubAndFeeds(@PathVariable clubId: Long): ClubResponse {

        return clubService.findClubAndFeed(clubId)
    }
}