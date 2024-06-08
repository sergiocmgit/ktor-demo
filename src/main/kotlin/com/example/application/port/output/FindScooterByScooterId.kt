package com.example.application.port.output

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId

interface FindScooterByScooterId {
    operator fun invoke(scooterId: ScooterId): Scooter
}
