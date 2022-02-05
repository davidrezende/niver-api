package com.eh.niver.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tb_person")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idPerson: Long?,
    @Column(name = "des_name")
    var name: String,
    @Column(name = "dat_birthday")
    var birthday: LocalDate,
    @Column(name = "des_email")
    var email: String,
    @Column(name = "desc_password")
    var password: String,
    @JsonIgnore
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "owner", fetch = FetchType.EAGER)
    var groupsCreated: List<Group>? = null,

    @JsonIgnore
    @ManyToMany(
        fetch = FetchType.EAGER,
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
                name = "id_person",
                nullable = false,
                updatable = false
            )
        ],
        inverseJoinColumns = [
            JoinColumn(
                name = "id_group",
                nullable = false,
                updatable = false
            )
        ],
        foreignKey = ForeignKey(value = ConstraintMode.CONSTRAINT),
        inverseForeignKey = ForeignKey(value = ConstraintMode.CONSTRAINT)
    )
    var groups: List<Group>? = null
)
