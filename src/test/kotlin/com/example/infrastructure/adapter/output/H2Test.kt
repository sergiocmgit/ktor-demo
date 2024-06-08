package com.example.infrastructure.adapter.output

import com.example.infrastructure.config.DatabaseInstance
import org.junit.jupiter.api.BeforeAll

interface H2Test {
    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            DatabaseInstance.init(H2Test::class.java.simpleName)
        }
    }
}
