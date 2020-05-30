package com.teamplay.domain.business.club.dto

import com.teamplay.domain.database.club.entity.Notice
import java.text.SimpleDateFormat

// 동호회 정보에 사용되는 가데이터를 위핸 임시 클래스임 .
data class SimpleNoticeInfo(
    val id: Long,
    val title: String,
    val createTime: String,
    val content: String
) {
    constructor(notice: Notice): this(
        notice.id!!,
        notice.title,
        SimpleDateFormat("yyyy-MM-dd").format(notice.createdDate),
        notice.content
    )
}