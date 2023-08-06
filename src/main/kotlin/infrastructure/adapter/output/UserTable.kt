package com.example.infrastructure.adapter.output

import com.example.application.domain.UserStatus
import org.jetbrains.exposed.sql.Table

object UserTable : Table() {
    val id = varchar("id", 128)
    val name = varchar("name", 128)
    val status = enumeration<UserStatus>("status")

    override val primaryKey = PrimaryKey(id)
}
