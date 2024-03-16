package service

import repository.Entity
import java.util.UUID

data class Analyze(
    override val id: UUID,
    val name: String,
) : Entity
