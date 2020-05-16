package com.teamplay.domain.database.jpa.match.repository.spec

import com.teamplay.domain.database.jpa.spec.PagingModel
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchStyle
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.Predicate

data class MatchSpecs(
        override val descending: Boolean = false,
        override val page: Int = 1,
        override val rowsPerPage: Int = 10,
        override val sortBy: String? = "createdDate",
        override val isAll: Boolean = false,

        val startTime: Date? = null,
        val location: String? = null,
        val matchStyle: MatchStyle? = null
) : PagingModel() {
    fun createSpecs(): Specification<Match>? {
        return Specification { match, _, builder ->

            val predicates = mutableListOf<Predicate>(builder.conjunction())

            startTime?.let {
                predicates.add(builder.equal(match.get<Date>("startTime"), it))
            }

            location?.let {
                predicates.add(builder.equal(match.get<String>("location"), it))
            }

            matchStyle?.let {
                predicates.add(builder.equal(match.get<MatchStyle>("matchStyle"), it))
            }

            builder.and(*predicates.toTypedArray())
        }
    }
}
