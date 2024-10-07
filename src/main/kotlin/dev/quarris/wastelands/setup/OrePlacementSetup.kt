package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object OrePlacementSetup {

    val COAL_VEIN_LOWER = register("coal_vein_lower")
    val COAL_VEIN_UPPER = register("coal_vein_upper")
    val LARGE_COAL_VEIN = register("large_coal_vein")

    private fun register(name: String) : ResourceKey<PlacedFeature> {
        return ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res(name))
    }

}