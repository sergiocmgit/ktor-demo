package infrastructure.adapter.input

import com.example.application.port.input.GetUsers
import com.example.infrastructure.adapter.input.GetUsersResponse
import com.example.infrastructure.adapter.input.users
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import fixtures.builders.buildUser
import fixtures.builders.buildUserDto
import fixtures.builders.randomUserId
import infrastructure.config.testRoutesModule
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserRoutesTest : RoutingTest() {
    private val getUsers = mockk<GetUsers>()

    private val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should get all the users`() =
        test {
            // Given
            val userId = randomUserId()
            every { getUsers() } returns listOf(buildUser(userId))
            // When
            val response = client.get("/users")
            val responseObject = objectMapper.readValue(response.bodyAsText(), GetUsersResponse::class.java)
            // Then
            assertThat(response.status).isEqualTo(OK)
            assertThat(responseObject).isEqualTo(GetUsersResponse(listOf(buildUserDto(userId))))
        }

    override fun ApplicationTestBuilder.appSetup() =
        application {
            testRoutesModule()
            install(Routing) {
                users(getUsers)
            }
        }
}
