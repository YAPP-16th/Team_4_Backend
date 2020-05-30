package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.club.error.NoticeDoseNotExist
import com.teamplay.domain.database.club.entity.Notice
import com.teamplay.domain.database.jpa.club.repository.NoticeRepository

class FindNoticeById(
    private val noticeRepository: NoticeRepository
): Function<Long, Notice> {
    override fun apply(id: Long): Notice {
        return noticeRepository.findById(id).orElseThrow { throw NoticeDoseNotExist() }
    }
}