package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature

object OreFeatureSetup {

    val CoalVein = register("coal_vein")
    val LargeCoalVein = register("large_coal_vein")

    private fun register(name: String) : ResourceKey<ConfiguredFeature<*, *>> {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res(name))
    }

}