package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.club.entity.Notice
import com.teamplay.domain.database.jpa.club.repository.NoticeRepository

class SaveNotice(
    private val noticeRepository: NoticeRepository
): Function<Notice, Notice> {
    override fun apply(notice: Notice): Notice {
        return noticeRepository.save(notice)
    }
}