package com.example.infrastructure.adapter.input

import com.example.application.port.input.GetUsers
import com.example.fixtures.builders.buildUser
import com.example.fixtures.builders.buildUserDto
import com.example.fixtures.builders.randomUserId
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.routing.Routing
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserRoutesTest : RoutingTest() {
    private val getUsers = mockk<GetUsers>()

    override fun Routing.setup() = users(getUsers)

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
}
