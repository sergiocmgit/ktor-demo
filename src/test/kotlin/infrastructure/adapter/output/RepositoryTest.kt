package infrastructure.adapter.output

import com.example.infrastructure.adapter.output.DatabaseFactory
import org.junit.jupiter.api.BeforeAll

abstract class RepositoryTest {

    // TODO: check how to avoid repo tests clashing
    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() { DatabaseFactory.init() }
    }
}
