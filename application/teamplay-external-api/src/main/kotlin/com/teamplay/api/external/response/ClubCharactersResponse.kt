package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.database.club.entity.ClubCharacter

data class ClubCharactersResponse(
    val characters: List<ClubCharacter>,
    override val message: String? = "클럽 성격 정보"
): Response()