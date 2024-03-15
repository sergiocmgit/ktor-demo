package component

import com.example.application.domain.ScooterStatus.LOCKED
import fixtures.DatabaseUtils.Companion.save
import fixtures.builders.buildScooter
import fixtures.builders.buildUser
import fixtures.builders.randomUserId
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.testApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random

class RunScooterTest : ComponentTest() {
    @Test
    fun `should run a scooter`() =
        testApplication {
            appSetup()
            // Given
            val userId = randomUserId().also { save(buildUser(userId = it)) }
            val scooterId = Random.nextInt().also { save(buildScooter(scooterId = it, status = LOCKED)) }
            // When
            val response = client.get("/scooters/$scooterId/run/$userId")
            // Then
            assertThat(response.status).isEqualTo(OK)
        }
}
