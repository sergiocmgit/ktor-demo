package component

import com.example.infrastructure.adapter.input.GetUsersResponse
import fixtures.DatabaseUtils.Companion.save
import fixtures.builders.buildUser
import fixtures.builders.buildUserDto
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.testApplication
import java.util.UUID.randomUUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetUsersTest : ComponentTest() {
    @Test
    fun `should get the users`() =
        testApplication {
            appSetup()
            // Given
            val userId = randomUUID().toString()
            buildUser(userId = userId).also(::save)
            // When
            val response = client.get("/users")
            val responseObject = objectMapper.readValue(response.bodyAsText(), GetUsersResponse::class.java)
            // Then
            assertThat(response.status).isEqualTo(OK)
            assertThat(responseObject).isEqualTo(GetUsersResponse(listOf(buildUserDto(userId = userId))))
        }
}
