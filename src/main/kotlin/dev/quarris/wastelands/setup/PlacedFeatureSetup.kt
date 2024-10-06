package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object PlacedFeatureSetup {

    val DEAD_WOOD = ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res("dead_wood"))
}