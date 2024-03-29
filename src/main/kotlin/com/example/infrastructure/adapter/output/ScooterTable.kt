package com.example.infrastructure.adapter.output

import com.example.application.domain.ScooterStatus
import org.jetbrains.exposed.sql.Table

object ScooterTable : Table() {
    val id = integer("id").autoIncrement()
    val status = enumeration<ScooterStatus>("status")
    val lastRider = varchar("last_rider", 128)

    override val primaryKey = PrimaryKey(id)
}
