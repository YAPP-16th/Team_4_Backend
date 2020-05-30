package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.club.entity.Notice
import com.teamplay.domain.database.jpa.club.repository.NoticeRepository

class FindNoticesByClubId(
    private val noticeRepository: NoticeRepository
): Function<Long, List<Notice>> {
    override fun apply(clubId: Long): List<Notice> {
        return noticeRepository.findAllByClub_Id(clubId)
    }
}