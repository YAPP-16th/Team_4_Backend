package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.club.dto.UserClubInfo

class UserClubsResponse(
    val clubsInfo: List<UserClubInfo>,
    override val message: String? = "사용자 클럽 정보"
): Response()