package com.example.application.port.output

import com.example.application.domain.Scooter

interface UpdateScooter {
    operator fun invoke(scooter: Scooter)
}
