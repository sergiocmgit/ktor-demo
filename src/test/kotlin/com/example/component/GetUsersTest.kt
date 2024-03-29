package com.example.component

import com.example.fixtures.DatabaseUtils.Companion.save
import com.example.fixtures.builders.buildUser
import com.example.fixtures.builders.buildUserDto
import com.example.infrastructure.adapter.input.GetUsersResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import java.util.UUID.randomUUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetUsersTest : ComponentTest() {
    @Test
    fun `should get the users`() =
        test {
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
