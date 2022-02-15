package com.eh.niver.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tb_group")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idGroup: Long?,
    @Column(name = "des_name")
    var name: String,

    @ManyToOne
    @JoinColumn(name = "id_owner")
    var owner: Person,

//    @JsonIgnore
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
        ]
    )
    @JoinTable(
        name = "ta_group_members",
        joinColumns = [
            JoinColumn(
                name = "id_group",
                nullable = false,
                updatable = false
            )
        ],
        inverseJoinColumns = [
            JoinColumn(
                name = "id_person",
                nullable = false,
                updatable = false
            )
        ],
        foreignKey = ForeignKey(value = ConstraintMode.CONSTRAINT),
        inverseForeignKey = ForeignKey(value = ConstraintMode.CONSTRAINT)
    )
    var members: MutableList<Person>? = null

)