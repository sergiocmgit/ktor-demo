package component

import com.example.application.port.input.GetUsersResponse
import fixtures.DatabaseUtils.Companion.save
import fixtures.builders.buildUser
import fixtures.builders.buildUserResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.testApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID

class GetUsersTest : ComponentTest() {
    @Test
    fun `should get the users`() =
        testApplication {
            appSetup()
            // Given
            val userId = randomUUID().toString()
            buildUser(userId = userId).also(::save)
            val expected = GetUsersResponse(listOf(buildUserResponse(userId = userId)))
            // When
            val response = client.get("/users")
            val responseObject = objectMapper.readValue(response.bodyAsText(), expected::class.java)
            // Then
            assertThat(response.status).isEqualTo(OK)
            assertThat(responseObject).isEqualTo(expected)
        }
}
