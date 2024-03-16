package service

import repository.Entity
import repository.Repository
import java.util.UUID

data class Analyze(
    override val id: UUID,
    val name: String,
    val variantRepo: Repository<Variant>,
    val criterionRepo: Repository<Criterion>,
) : Entity

data class Variant (
    override val id: UUID,
    val name: String,
    val properties: List<Property>
) : Entity

data class Criterion (
    override val id: UUID,
    val name: String,
    val weight: Int
) : Entity

data class Property(
    val criterionId: UUID,
    val value: String,
)