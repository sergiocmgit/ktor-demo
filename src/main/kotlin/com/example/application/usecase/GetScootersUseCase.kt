package com.example.application.usecase

import com.example.application.domain.Scooter
import com.example.application.port.input.GetScooters
import com.example.application.port.output.FindAllScooters

class GetScootersUseCase(
    private val findAllScooters: FindAllScooters,
) : GetScooters {
    override fun invoke(): List<Scooter> = findAllScooters()
}
