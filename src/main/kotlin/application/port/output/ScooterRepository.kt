package com.example.application.port.output

import arrow.core.Either
import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterNotFound

interface ScooterRepository {

    fun findAll(): List<Scooter>

    fun find(scooterId: ScooterId): Either<ScooterNotFound, Scooter>

    fun update(scooter: Scooter)
}
