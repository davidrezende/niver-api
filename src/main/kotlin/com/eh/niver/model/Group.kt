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
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_owner")
    var owner: Person,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "ta_group_members",
        joinColumns = [JoinColumn(name = "id_group")],
        inverseJoinColumns = [JoinColumn(name = "id_person")]
    )
    var people: List<Person>? = null

)