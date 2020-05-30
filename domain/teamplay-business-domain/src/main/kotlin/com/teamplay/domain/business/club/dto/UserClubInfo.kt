package com.teamplay.domain.business.club.dto

import com.teamplay.domain.database.club.entity.Club

data class UserClubInfo(
    val clubId: Long,
    val name: String,
    val location: String,
    val createdDate: String,
    val memberCount: Int,
    val userRole: ClubRole
) {
    constructor(club: Club, memberCount: Int, role: ClubRole): this(
        club.id!!,
        club.name,
        club.location,
        club.createTeamDate,
        memberCount,
        role
    )
}