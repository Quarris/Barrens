package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.ConfiguredFeatureSetup
import dev.quarris.wastelands.setup.PlacedFeatureSetup
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.util.valueproviders.BiasedToBottomInt
import net.minecraft.world.level.levelgen.placement.*

object PlacedFeatureGen : RegistryBootstrap<PlacedFeature> {
    override fun run(context: BootstrapContext<PlacedFeature>) {
        val features = context.lookup(Registries.CONFIGURED_FEATURE)

        val deadWoodFeature = features.getOrThrow(ConfiguredFeatureSetup.DEAD_OAK_TREE)
        val slateBoulderFeature = features.getOrThrow(ConfiguredFeatureSetup.SLATE_BOULDER)
        val driedGrass = features.getOrThrow(ConfiguredFeatureSetup.DRIED_GRASS)
        val singleDriedGrass = features.getOrThrow(ConfiguredFeatureSetup.SINGLE_DRIED_GRASS)
        val waterLake = features.getOrThrow(ConfiguredFeatureSetup.DRIED_DIRT_WATER_LAKE)

        context.register(
            PlacedFeatureSetup.DEAD_OAK_TREE, PlacedFeature(
                deadWoodFeature, listOf(
                    RarityFilter.onAverageOnceEvery(5),
                    CountPlacement.of(BiasedToBottomInt.of(1, 3)),
                    InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP,
                    BiomeFilter.biome()
                )
            )
        )

        context.register(
            PlacedFeatureSetup.SLATE_BOULDER, PlacedFeature(
                slateBoulderFeature, listOf(
                    RarityFilter.onAverageOnceEvery(14),
                    InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP,
                    BiomeFilter.biome()
                )
            )
        )

        context.register(
            PlacedFeatureSetup.DRIED_GRASS_PATCH, PlacedFeature(
                driedGrass, listOf(
                    NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                    InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                    PlacementUtils.isEmpty(),
                    BiomeFilter.biome()
                )
            )
        )

        context.register(
            PlacedFeatureSetup.DRIED_GRASS_BONEMEAL, PlacedFeature(singleDriedGrass, listOf(PlacementUtils.isEmpty()))
        )

        context.register(
            PlacedFeatureSetup.DRIED_DIRT_WATER_LAKE, PlacedFeature(
                waterLake, listOf(
                    RarityFilter.onAverageOnceEvery(20),
                    PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                    BiomeFilter.biome()
                )
            )
        )
    }
}