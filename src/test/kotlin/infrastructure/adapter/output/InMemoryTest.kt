package infrastructure.adapter.output

import com.example.infrastructure.config.DatabaseFactory
import org.junit.jupiter.api.BeforeAll

interface InMemoryTest {
    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            DatabaseFactory.init(InMemoryTest::class.java.simpleName)
        }
    }
}
