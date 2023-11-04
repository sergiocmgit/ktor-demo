package com.example.infrastructure.adapter.input

import io.ktor.resources.Resource

@Resource("/scooters")
class Scooter {
    @Resource("/{scooterId}")
    class Id(val parent: Scooter, val scooterId: Int) {
        @Resource("/run/{userId}")
        class Run(val parent: Id, val userId: String)

        @Resource("/lock/{userId}")
        class Lock(val parent: Id, val userId: String)
    }
}
