package component

import com.example.infrastructure.adapter.output.DatabaseFactory
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import com.example.infrastructure.adapter.output.ScooterTable
import com.example.infrastructure.adapter.output.UserTable
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import config.testRoutingModule
import infrastructure.config.testRoutesModule
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
            DatabaseFactory.init(ComponentTest::class.java.simpleName)
            transaction {
                ScooterTable.deleteAll()
                UserTable.deleteAll()
            }
        }

        @JvmStatic
        private fun ApplicationTestBuilder.appSetup() =
            application {
                testRoutesModule()

                val inMemoryScooters = InMemoryScooters()
                val inMemoryUsers = InMemoryUsers()
                testRoutingModule(inMemoryScooters, inMemoryUsers)
            }

        @JvmStatic
        protected fun test(block: suspend ApplicationTestBuilder.() -> Unit): Unit =
            testApplication {
                appSetup()
                block()
            }
    }
}
