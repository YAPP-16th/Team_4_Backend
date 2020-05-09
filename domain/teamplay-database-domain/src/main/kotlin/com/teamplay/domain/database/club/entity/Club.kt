package com.teamplay.domain.database.club.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.user.entity.Ability
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "clubs")
data class Club(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long?,

    @Column(nullable = false)
    var name: String,

    @Enumerated
    @Column(nullable = false)
    var category: Category,

    var location: String? = null,

    var emblem: String? = null,

    @Enumerated
    @Column(nullable = false)
    var ability: Ability,

    var thumbnail: String? = null,

    var introduce: String? = null,

    var contact: String? = null,

    @CreationTimestamp
    @Column(nullable = false)
    val createdDate: Date,

    @UpdateTimestamp
    var updatedDate: Date? = null,

    @OneToMany(mappedBy = "club")
    var members: MutableList<ClubMember> = mutableListOf<ClubMember>()
): EntityId