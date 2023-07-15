package com.example.infrastructure.driver

import io.ktor.resources.Resource

@Resource("/{scooterId}")
class Id(val scooterId: Int) {

    @Resource("/lock/{userId}")
    class Lock(val parent: Id, val userId: String)
}