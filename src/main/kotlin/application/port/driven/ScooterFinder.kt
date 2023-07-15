package com.example.application.port.driven

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId

interface ScooterFinder {

    fun find(scooterId: ScooterId): Scooter?

    fun findAll(): List<Scooter>
}