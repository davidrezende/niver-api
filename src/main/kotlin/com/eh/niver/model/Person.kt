package com.eh.niver.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tb_person")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idPerson: Long,
    @Column(name = "des_name")
    var name: String,
    @Column(name = "dat_birthday")
    var birthday: LocalDate,
    @Column(name = "des_email")
    var email: String,
    @Column(name = "desc_password")
    var password: String,
    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "owner")
    var groups: List<Group>? = null
)
