package com.example.infrastructure.adapter.output

import com.example.infrastructure.config.DatabaseInstance
import org.junit.jupiter.api.BeforeAll

interface InMemoryTest {
    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            DatabaseInstance.init(InMemoryTest::class.java.simpleName)
        }
    }
}
