package com.teamplay.domain.business.club.dto

// 동호회 정보에 사용되는 가데이터를 위핸 임시 클래스임 .
data class SimpleFeeds(
    val type: Int,
    val simpleResultInfo: SimpleResultInfo? = null,
    val simpleNoticeInfo: SimpleNoticeInfo? = null
)