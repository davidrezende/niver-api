package com.eh.niver.model

import javax.persistence.*

@Entity
@Table(name = "tb_group")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idGroup: Long?,
    @Column(name = "des_name")
    var name: String,
    @ManyToOne
    @JoinColumn(name = "id_owner")
    var owner: Person

)