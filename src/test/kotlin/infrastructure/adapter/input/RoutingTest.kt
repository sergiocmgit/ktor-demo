package infrastructure.adapter.input

import io.ktor.server.testing.ApplicationTestBuilder

abstract class RoutingTest {
    abstract fun ApplicationTestBuilder.appSetup()
}