package com.teamplay.domain.database.jpa.club.repository

import com.teamplay.domain.database.club.entity.ClubAdmin
import com.teamplay.domain.database.jpa.ExtendedRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ClubAdminRepository : ExtendedRepository<ClubAdmin> {
    fun findAllByClubId(clubId: Long): MutableList<ClubAdmin>

    fun findAllByUserId(userId: Long): List<ClubAdmin>

    @Query("""
        SELECT CASE WHEN COUNT(ca) > 0 THEN true ELSE false END 
        FROM ClubAdmin ca
        WHERE ca.user.id = :userId
        AND ca.club.id = :clubId
    """)
    fun checkIsClubAdmin(userId: Long, clubId: Long): Boolean
}
