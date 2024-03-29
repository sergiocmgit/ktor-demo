package com.example.infrastructure.adapter.input

import io.ktor.resources.Resource

@Resource("/scooters")
class Scooters {
    @Resource("/{scooterId}")
    class Id(val parent: Scooters, val scooterId: Int) {
        @Resource("/run/{userId}")
        class Run(val parent: Id, val userId: String)

        @Resource("/lock/{userId}")
        class Lock(val parent: Id, val userId: String)
    }
}
