package com.teamplay.api.com.teamplay.api.external.controller

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clubs")
class ClubController {

    @ApiOperation(value = "동호회 목록 정보")
    @GetMapping
    fun find(): String {
        // 동호회 목록 페이징 처리하여 리턴할지 고민.
        // 찾기도 여기로 검색
        // spec 정의해서
        return "동호회 목록 정보"
    }

    @ApiOperation(value = "동호회 상세 정보")
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): String {
        // 동호회 목록 페이징 처리하여 리턴할지 고민.
        return "동호회 상세 정보"
    }

    @ApiOperation(value = "가입한 동호회 리스트")
    @GetMapping("/{userId}")
    fun getJoinedClub(@PathVariable userId: Long): String {
        return "가입한 동호회 리스트"
    }

    @ApiOperation(value = "추천 동호회 리스트")
    @GetMapping("/recommendation")
    fun recommendationClub(): String {
        // 동호회 추천 알고리즘 고민 필요
        return "추천 동호회 리스트"
    }

    @ApiOperation(value = "동호회 뉴스피드")
    @GetMapping("/feed")
    fun getNewsFeed(): String {
        return "동호회 뉴스피드"
    }

    @ApiOperation(value = "동호회 생성")
    @PostMapping
    fun save(): String {
        return "동호회 생성"
    }

    @ApiOperation(value = "피드 생성")
    @PostMapping("/feed")
    fun createFeed(): String {
        return "피드 생성"
    }

    @ApiOperation(value = "동호회 가입 요청")
    @PostMapping("/join")
    fun joinClubRequest(): String {
        // 가입 요청했을 경우, 우선 동호회 테이블에 유저 추가하고 type 으로 가입 대기|멤버|탈퇴 enum 으로 만들고 save 하나만 뚫어두면 좋을거같습니다.
        return "동호회 가입 요청"
    }

    @ApiOperation(value = "동호회에 속한 멤버 상태 변경")
    @PutMapping("/{id}")
    fun updateClubMember(@PathVariable id: Long): String {
        return "동호회에 속한 멤버 상태 변경"
    }
}