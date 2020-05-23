package com.teamplay.domain.business.club.dto

data class ClubListInfo(
    val id: Long,
    val name: String,
    val location: String,
    val memberCount: Int
)