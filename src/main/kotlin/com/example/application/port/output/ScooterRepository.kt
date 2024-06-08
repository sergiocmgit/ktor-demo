package com.example.application.port.output

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId

interface ScooterRepository {
    fun findBy(scooterId: ScooterId): Scooter

    fun update(scooter: Scooter)
}
