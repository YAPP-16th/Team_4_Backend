package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match
import org.springframework.transaction.annotation.Transactional

open class SaveMatchFunction(
    private val repository: MatchRepository
): Function<Match, Match> {
    @Transactional
    override fun apply(match: Match): Match {
        return repository.save(match.prepareForSave())
    }
}
