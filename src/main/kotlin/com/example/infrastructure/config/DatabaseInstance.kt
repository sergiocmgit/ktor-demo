package com.example.infrastructure.config

import com.example.application.domain.ScooterStatus
import com.example.application.domain.UserStatus
import com.example.infrastructure.adapter.output.ScooterTable
import com.example.infrastructure.adapter.output.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseInstance {
    fun init(databaseName: String = "db") {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/$databaseName"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(ScooterTable)
            UserTable.deleteAll()
            ScooterTable.deleteAll()
            initUserTable()
            initScooterTable()
        }
    }

    private fun initUserTable() {
        UserTable.batchInsert(
            listOf(
                Triple("A", "Antonio", UserStatus.ACTIVE),
                Triple("B", "Barry", UserStatus.DEACTIVATED),
            ),
        ) { (id, name, status) ->
            this[UserTable.id] = id
            this[UserTable.name] = name
            this[UserTable.status] = status
        }
    }

    private fun initScooterTable() {
        ScooterTable.batchInsert(
            listOf(
                Triple(1, ScooterStatus.LOCKED, "A"),
                Triple(2, ScooterStatus.RUNNING, "A"),
                Triple(3, ScooterStatus.LOCKED, "B"),
            ),
        ) { (id, status, lastRider) ->
            this[ScooterTable.id] = id
            this[ScooterTable.status] = status
            this[ScooterTable.lastRider] = lastRider
        }
    }
}
