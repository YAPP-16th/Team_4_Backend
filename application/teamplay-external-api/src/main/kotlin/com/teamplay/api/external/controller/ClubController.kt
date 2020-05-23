package com.teamplay.api.com.teamplay.api.external.controller

import com.teamplay.api.com.teamplay.api.external.request.CreateClubRequest
import com.teamplay.api.com.teamplay.api.external.request.GetClubsRequest
import com.teamplay.api.com.teamplay.api.external.request.JoinClubRequest
import com.teamplay.api.com.teamplay.api.external.response.ClubResponse
import com.teamplay.api.com.teamplay.api.external.response.ClubsResponse
import com.teamplay.api.com.teamplay.api.external.response.ClubJoinInfoResponse
import com.teamplay.api.com.teamplay.api.external.response.CreateClubResponse
import com.teamplay.api.com.teamplay.api.external.service.AuthService
import com.teamplay.api.com.teamplay.api.external.service.ClubService
import com.teamplay.domain.database.club.entity.ClubCharacter
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
        @Valid @RequestHeader accessToken: String,
        @RequestBody createClubRequest: CreateClubRequest
    ): CreateClubResponse {
        val user = authService.getUserByAccessToken(accessToken)
        return clubService.registerClub(createClubRequest, user)
    }

    @ApiOperation(value = "동호회 가입")
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    fun joinClub(
        @Valid @RequestHeader accessToken: String,
        @RequestBody clubId: Long
    ): ClubResponse{
        val user = authService.getUserByAccessToken(accessToken)

        return clubService.joinClub(
            JoinClubRequest(user.id!!, clubId)
        )
    }

    @ApiOperation(value = "동호회 가입 정보 얻기")
    @GetMapping("/join/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    fun getClubJoinInfo(@PathVariable clubId: Long): ClubJoinInfoResponse {

        return clubService.findClubJoinInfo(clubId)
    }

    @ApiOperation(value = "동호회 검색")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getClubs(getClubsRequest: GetClubsRequest) = getClubsByName("",getClubsRequest)

    @ApiOperation(value = "동호회 이름으로 검색")
    @GetMapping("/names/{name}")
    @ResponseStatus(HttpStatus.OK)
    fun getClubsByName(@PathVariable name: String, getClubsRequest: GetClubsRequest): ClubsResponse {

        return clubService.findClubInfosByName(name, getClubsRequest)
    }

    @ApiOperation(value = "동호회 주소로 검색")
    @GetMapping("/addresses/{address}")
    @ResponseStatus(HttpStatus.OK)
    fun getClubsByAddress(@PathVariable address: String, getClubsRequest: GetClubsRequest): ClubsResponse {

        return clubService.findClubInfosByAddress(address, getClubsRequest)
    }

    @ApiOperation(value = "동호회 성격으로 검색")
    @GetMapping("/characters/")
    @ResponseStatus(HttpStatus.OK)
    fun getClubsByCharacters(characters: Array<ClubCharacter>, getClubsRequest: GetClubsRequest): ClubsResponse {

        return clubService.findClubInfosByCharacters(characters.asList(), getClubsRequest)
    }

    @ApiOperation(value = "동호회 정보")
    @GetMapping("/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    fun getClubAndFeeds(@PathVariable clubId: Long): ClubResponse {

        return clubService.findClubAndFeed(clubId)
    }

    @ApiOperation(value = "동호회 성격 정보 얻기")
    @GetMapping("/characters/infos")
    @ResponseStatus(HttpStatus.OK)
    fun getClubCharacters(): List<ClubCharacter> {

        return ClubCharacter.values().asList()
    }
}