package com.teamplay.domain.database.jpa.club.repository

import com.teamplay.domain.database.club.entity.Notice
import com.teamplay.domain.database.jpa.ExtendedRepository
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository : ExtendedRepository<Notice> {
    fun findAllByClub_Id(clubId: Long) : List<Notice>
}