package com.example.application.port.output

import com.example.application.domain.Scooter

interface ScooterRepository {
    fun update(scooter: Scooter)
}
