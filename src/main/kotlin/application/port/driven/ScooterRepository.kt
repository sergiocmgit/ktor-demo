package com.example.application.port.driven

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId

interface ScooterRepository {

    fun find(scooterId: ScooterId): Scooter?

    fun findAll(): List<Scooter>

    fun update(scooter: Scooter)
}