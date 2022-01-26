package com.eh.niver.repository

import com.eh.niver.model.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GroupRepository : JpaRepository<Group, Long> {
}