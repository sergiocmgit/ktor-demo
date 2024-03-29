package com.example.infrastructure.config

import com.example.infrastructure.adapter.output.ScooterTable
import com.example.infrastructure.adapter.output.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(databaseName: String = "db") {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/$databaseName"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(ScooterTable)
            SchemaUtils.create(UserTable)
        }
    }
}
