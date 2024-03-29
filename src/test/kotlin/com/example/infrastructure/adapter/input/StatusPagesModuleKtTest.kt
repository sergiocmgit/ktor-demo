package com.example.infrastructure.adapter.input

import com.example.application.domain.ScooterNotFound
import com.example.application.domain.UserNotFound
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class StatusPagesModuleKtTest {
    @TestFactory
    fun `should handle not found errors`() =
        listOf(
            UserNotFound,
            ScooterNotFound,
        ).map { domainException ->
            dynamicTest(domainException.name) {
                test {
                    // When
                    val response = client.get(domainException.name)
                    // Then
                    assertThat(response.status).isEqualTo(NotFound)
                }
            }
        }

    private fun test(block: suspend ApplicationTestBuilder.() -> Unit): Unit =
        testApplication {
            install(Routing) { buildRoutes() }
            application { statusPages() }
            block()
        }

    private fun Route.buildRoutes() =
        listOf(
            UserNotFound,
            ScooterNotFound,
        ).map { domainException -> get(domainException.name) { throw domainException } }

    private val Exception.name get() = javaClass.simpleName
}
