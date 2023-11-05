package com.example.infrastructure.adapter.output

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

// TODO: change to postgres DB to avoid H2 file locking
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
