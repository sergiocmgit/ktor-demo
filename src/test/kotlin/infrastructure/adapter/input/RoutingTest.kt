package infrastructure.adapter.input

import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication

abstract class RoutingTest {
    abstract fun ApplicationTestBuilder.appSetup()

    protected fun test(block: suspend ApplicationTestBuilder.() -> Unit): Unit =
        testApplication {
            appSetup()
            block()
        }
}
