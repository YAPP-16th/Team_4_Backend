package com.teamplay.domain.database.club.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.user.entity.User
import javax.persistence.*

@Entity
@Table(name = "club_admins")
data class ClubAdmin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long?,

    @ManyToOne
    @JoinColumn(name = "clubs_id")
    var club: Club,

    @ManyToOne
    @JoinColumn(name = "users_id")
    var user: User
): EntityId