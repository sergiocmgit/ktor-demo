package component

import com.example.infrastructure.adapter.output.DatabaseFactory
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import config.testRoutingModule
import infrastructure.config.testRoutesModule
import io.ktor.server.testing.ApplicationTestBuilder
import org.junit.jupiter.api.BeforeAll

interface ComponentTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            DatabaseFactory.init(ComponentTest::class.java.simpleName)
        }

        fun ApplicationTestBuilder.appSetup() = application {
            testRoutesModule()

            val inMemoryScooters = InMemoryScooters()
            val inMemoryUsers = InMemoryUsers()
            testRoutingModule(inMemoryScooters, inMemoryUsers)
        }
    }
}
