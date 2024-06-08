package com.example.application.port.output

import com.example.application.domain.Scooter

interface FindAllScooters {
    operator fun invoke(): List<Scooter>
}
