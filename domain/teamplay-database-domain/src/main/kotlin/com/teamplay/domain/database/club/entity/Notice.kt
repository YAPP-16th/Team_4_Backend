package com.teamplay.domain.database.club.entity

import com.teamplay.core.database.EntityId
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "notices")
data class Notice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long?,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "club_id")
    var club: Club,

    @CreationTimestamp
    @Column(nullable = false)
    val createdDate: Date = Date(),

    @UpdateTimestamp
    var updatedDate: Date? = null
): EntityId