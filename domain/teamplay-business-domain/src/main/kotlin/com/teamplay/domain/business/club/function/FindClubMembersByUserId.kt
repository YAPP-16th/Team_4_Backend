package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.club.entity.ClubMember
import com.teamplay.domain.database.jpa.club.repository.ClubMemberRepository

class FindClubMembersByUserId(
    private val clubMemberRepository: ClubMemberRepository
): Function<Long, List<ClubMember>> {
    override fun apply(userId: Long): List<ClubMember> {
        return clubMemberRepository.findAllByUserId(userId)
    }
}