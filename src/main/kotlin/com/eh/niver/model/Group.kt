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
    @OneToOne(fetch=FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @PrimaryKeyJoinColumn(name = "id_owner")
//    @JoinColumn
    var owner: Person
)