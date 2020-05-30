package com.teamplay.domain.business.club.dto

import com.teamplay.domain.database.club.entity.Club

data class UserClubInfo(
    val id: Long,
    val name: String,
    val location: String,
    val memberCount: Int,
    val role: ClubRole
) {
    constructor(club: Club, memberCount: Int, role: ClubRole): this(
        club.id!!,
        club.name,
        club.location,
        memberCount,
        role
    )
}