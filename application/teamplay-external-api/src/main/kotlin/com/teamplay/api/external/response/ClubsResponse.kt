package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.club.dto.ClubInfo
import com.teamplay.domain.business.club.dto.ClubListInfo

class ClubsResponse(
    val clubListInfo: MutableList<ClubListInfo>,
    val totalPage: Int,
    val currentPage: Int,
    val resultCount: Long,
    override val message: String? = "동호회 목록 불러오기"
): Response()