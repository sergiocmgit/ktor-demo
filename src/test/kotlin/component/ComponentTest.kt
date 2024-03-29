package component
// TODO: fix packages, choose a prefix and set up the directories

import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import com.example.infrastructure.adapter.output.ScooterTable
import com.example.infrastructure.adapter.output.UserTable
import com.example.infrastructure.config.DatabaseFactory
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
            DatabaseFactory.init(ComponentTest::class.java.simpleName)
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
                routingModule(inMemoryScooters, inMemoryUsers)
            }

        @JvmStatic
        protected fun test(block: suspend ApplicationTestBuilder.() -> Unit): Unit =
            testApplication {
                appSetup()
                block()
            }
    }
}
