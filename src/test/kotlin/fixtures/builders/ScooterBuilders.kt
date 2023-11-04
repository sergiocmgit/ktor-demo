package com.example.fixtures.builders

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterStatus
import com.example.application.domain.ScooterStatus.LOCKED
import com.example.application.domain.UserId
import com.example.application.port.input.ScooterResponse

const val DEFAULT_SCOOTER_ID: Int = 1

fun buildScooter(status: ScooterStatus = LOCKED) =
    Scooter(
        ScooterId(DEFAULT_SCOOTER_ID),
        status,
        UserId(DEFAULT_USER_ID),
    )

fun buildScooterResponse() =
    ScooterResponse(
        DEFAULT_SCOOTER_ID,
        LOCKED,
        DEFAULT_USER_ID,
    )
