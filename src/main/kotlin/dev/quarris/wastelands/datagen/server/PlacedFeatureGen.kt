package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.ConfiguredFeatureSetup
import dev.quarris.wastelands.setup.PlacedFeatureSetup
import net.minecraft.core.Holder
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.util.valueproviders.IntProvider
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.placement.BiomeFilter
import net.minecraft.world.level.levelgen.placement.CountPlacement
import net.minecraft.world.level.levelgen.placement.InSquarePlacement
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraft.world.level.levelgen.placement.RarityFilter

object PlacedFeatureGen : RegistryBootstrap<PlacedFeature> {
    override fun run(context: BootstrapContext<PlacedFeature>) {
        val configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE)
        val deadWoodFeature: Holder<ConfiguredFeature<*, *>> =
            configuredFeatures.getOrThrow(ConfiguredFeatureSetup.DEAD_WOOD)
        context.register(
            PlacedFeatureSetup.DEAD_WOOD, PlacedFeature(
                deadWoodFeature, listOf(
                    RarityFilter.onAverageOnceEvery(5),
                    InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP,
                    BiomeFilter.biome()
                )
            )
        )
    }
}