package com.example.component

import com.example.infrastructure.adapter.output.H2FindAllUsers
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import com.example.infrastructure.adapter.output.ScooterTable
import com.example.infrastructure.adapter.output.UserTable
import com.example.infrastructure.config.DatabaseInstance
import com.example.infrastructure.config.routingModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll

abstract class ComponentTest {
    protected val objectMapper = jacksonObjectMapper()

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            DatabaseInstance.init(ComponentTest::class.java.simpleName)
            transaction {
                ScooterTable.deleteAll()
                UserTable.deleteAll()
            }
        }

        @JvmStatic
        private fun ApplicationTestBuilder.appSetup() =
            application {
                val inMemoryScooters = InMemoryScooters()
                val inMemoryUsers = InMemoryUsers()
                val findAllUsers = H2FindAllUsers()
                routingModule(inMemoryScooters, inMemoryUsers, findAllUsers)
            }

        @JvmStatic
        protected fun test(block: suspend ApplicationTestBuilder.() -> Unit): Unit =
            testApplication {
                appSetup()
                block()
            }
    }
}
