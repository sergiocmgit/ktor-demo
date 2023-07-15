package com.example.infrastructure.driver

import io.ktor.resources.Resource

@Resource("/scooters")
class Scooters {

    @Resource("/{scooterId}")
    class Id(val parent: Scooters, val scooterId: Int) {

        @Resource("/lock/{userId}")
        class Lock(val parent: Id, val userId: String)
    }
}