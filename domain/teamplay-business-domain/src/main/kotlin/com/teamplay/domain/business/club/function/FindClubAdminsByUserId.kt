package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.club.entity.ClubAdmin
import com.teamplay.domain.database.jpa.club.repository.ClubAdminRepository

class FindClubAdminsByUserId(
    private val clubAdminRepository: ClubAdminRepository
): Function<Long, List<ClubAdmin>> {
    override fun apply(userId: Long): List<ClubAdmin> {
        return clubAdminRepository.findAllByUserId(userId)
    }
}