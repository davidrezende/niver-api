package com.eh.niver.repository

import com.eh.niver.model.Group
import com.eh.niver.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface PersonRepository : JpaRepository<Person, Long> {
    fun findByEmail(email: String): Person

    @Query(
        value = "SELECT a.*\n" +
                "FROM \n" +
                " (SELECT tb_person.*,\n" +
                "  case -- verifica se o ano atual é bissexto\n" +
                "    when mod(extract(year from current_date), 400) = 0\n" +
                "         OR\n" +
                "         (mod(extract(year from current_date), 100) <> 0\n" +
                "          AND mod(extract(year from current_date), 4) = 0)\n" +
                "    then 1\n" +
                "    else 0\n" +
                "  end as ano_bissexto\n" +
                "  FROM tb_person) a\n" +
                "WHERE\n" +
                "  to_char(a.dat_birthday, 'MM-DD') = to_char(current_date, 'MM-DD')\n" +
                "  OR\n" +
                "  (to_char(a.dat_birthday, 'MM-DD') = '02-29' AND a.ano_bissexto = 0\n" +
                "   AND to_char(current_date, 'MM-DD') = '02-28')",
        nativeQuery = true)
    fun findByBirthdaysForToday(birthday: LocalDate): List<Person>
    fun findByIdPerson( idPerson: Long): Optional<Person>
}
