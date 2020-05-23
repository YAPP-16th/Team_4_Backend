package com.teamplay.domain.database.jpa.club.repository

import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.club.entity.ClubCharacter
import com.teamplay.domain.database.jpa.ExtendedRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClubRepository : ExtendedRepository<Club> {

    fun existsByName(name: String): Boolean

    fun findAllByNameContaining(name: String, pageable: Pageable): Page<Club>

    fun findAllByLocationContaining(location: String, pageable: Pageable): Page<Club>

    @Query("SELECT c FROM Club c LEFT JOIN c.characters cc WHERE cc IN (:characters) GROUP BY c.id")
    fun findAllByCharactersIn(@Param("characters")characters: List<ClubCharacter>, pageable: Pageable): Page<Club>

}