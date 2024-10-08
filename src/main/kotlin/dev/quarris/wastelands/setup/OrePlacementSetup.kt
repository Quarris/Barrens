package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object OrePlacementSetup {

    val CoalVeinLower = register("coal_vein_lower")
    val CoalVeinUpper = register("coal_vein_upper")
    val LargeCoalVein = register("large_coal_vein")

    private fun register(name: String) : ResourceKey<PlacedFeature> {
        return ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res(name))
    }

}