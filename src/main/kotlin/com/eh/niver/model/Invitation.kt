package com.eh.niver.model

import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tb_invitation")
data class Invitation(
    @Id
    var uuidHash:UUID = UUID.randomUUID(),
    @Column(name = "used")
    var used: Long?,
    @Column(name = "dat_creation")
    var creation: LocalDate,

    @OneToOne
    @JoinColumn(name = "id_group")
    var group: Group,

    )