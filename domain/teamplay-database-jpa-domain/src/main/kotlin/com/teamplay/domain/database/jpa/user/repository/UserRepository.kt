package com.teamplay.domain.database.jpa.user.repository

import com.teamplay.domain.database.jpa.ExtendedRepository
import com.teamplay.domain.database.user.entity.User
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface UserRepository: ExtendedRepository<User> {

    fun findByEmail(email: String): Optional<User>

    fun findByNicknameOrEmail(nickname: String, email: String): Optional<User>

    fun existsByEmail(email: String): Boolean

    fun existsByNickname(nickname: String): Boolean

    @Transactional
    @Modifying
    @Query("update User user set user.hashedPassword = :hashedPassword where user.id = :id")
    fun updateHashedPasswordById(id: Int, hashedPassword: String)

    @Transactional
    @Modifying
    @Query("update User user set user.nickname = :nickname where user.id = :id")
    fun updateNicknameById(id: Int, nickname: String)

}