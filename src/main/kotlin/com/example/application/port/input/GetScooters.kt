package com.example.application.port.input

import com.example.application.domain.Scooter

interface GetScooters {
    operator fun invoke(): List<Scooter>
}
