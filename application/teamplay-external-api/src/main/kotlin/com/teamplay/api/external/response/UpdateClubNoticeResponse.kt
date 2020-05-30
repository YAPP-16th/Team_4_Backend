package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.club.dto.SimpleNoticeInfo

data class UpdateClubNoticeResponse(
    val simpleNoticeInfo: SimpleNoticeInfo,
    override val message: String? = "동호회 공지사항 업데이트"
): Response()