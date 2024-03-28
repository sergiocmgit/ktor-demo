package com.example.infrastructure.adapter.input

import io.ktor.resources.Resource

@Resource("/scooters")
class ScooterResource {
    @Resource("/{scooterId}")
    class Id(val parent: ScooterResource, val scooterId: Int) {
        @Resource("/run/{userId}")
        class Run(val parent: Id, val userId: String)

        @Resource("/lock/{userId}")
        class Lock(val parent: Id, val userId: String)
    }
}
