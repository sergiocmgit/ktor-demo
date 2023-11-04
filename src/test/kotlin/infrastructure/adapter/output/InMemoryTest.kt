package infrastructure.adapter.output

import com.example.infrastructure.adapter.output.DatabaseFactory
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
