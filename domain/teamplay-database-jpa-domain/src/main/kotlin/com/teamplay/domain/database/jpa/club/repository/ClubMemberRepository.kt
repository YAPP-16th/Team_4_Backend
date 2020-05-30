package com.teamplay.domain.database.jpa.club.repository

import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.club.entity.ClubMember
import com.teamplay.domain.database.jpa.ExtendedRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClubMemberRepository : ExtendedRepository<ClubMember> {

    fun findAllByClubId(clubId: Long): MutableList<ClubMember>

    fun findAllByUserId(userId: Long): List<ClubMember>

    fun existsByClub_IdAndUser_Id(clubId: Long, userId: Long): Boolean
}
