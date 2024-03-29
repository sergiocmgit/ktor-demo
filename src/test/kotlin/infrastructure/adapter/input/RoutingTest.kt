package infrastructure.adapter.input

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.Routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json

abstract class RoutingTest {
    abstract fun Routing.setup()

    protected fun test(block: suspend ApplicationTestBuilder.() -> Unit): Unit =
        testApplication {
            install(ContentNegotiation) { json(Json) }
            install(Resources)
            application {
                install(Routing) {
                    setup()
                }
            }
            block()
        }
}
