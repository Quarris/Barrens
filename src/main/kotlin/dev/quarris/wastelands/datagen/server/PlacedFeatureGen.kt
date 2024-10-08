package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.*
import net.minecraft.core.BlockPos
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.util.valueproviders.BiasedToBottomInt
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.placement.*

object PlacedFeatureGen : RegistryBootstrap<PlacedFeature> {
    override fun run(context: BootstrapContext<PlacedFeature>) {
        val features = context.lookup(Registries.CONFIGURED_FEATURE)

        val deadWoodFeature = features.getOrThrow(ConfiguredFeatureSetup.DEAD_OAK_TREE)
        val slateBoulderFeature = features.getOrThrow(ConfiguredFeatureSetup.SLATE_BOULDER)
        val driedGrass = features.getOrThrow(ConfiguredFeatureSetup.DRIED_GRASS)
        val singleDriedGrass = features.getOrThrow(ConfiguredFeatureSetup.SINGLE_DRIED_GRASS)
        val waterLake = features.getOrThrow(ConfiguredFeatureSetup.DRIED_DIRT_WATER_LAKE)
        val deadSeagrassSingle = features.getOrThrow(ConfiguredFeatureSetup.DEAD_SEAGRASS_SINGLE)
        val deadSeagrass = features.getOrThrow(ConfiguredFeatureSetup.DEAD_SEAGRASS)

        registerOres(context)

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

        context.register(
            PlacedFeatureSetup.DEAD_SEAGRASS_PATCH, PlacedFeature(
                deadSeagrass, seagrassPlacement(20, 50)
            )
        )
    }

    private fun registerOres(context: BootstrapContext<PlacedFeature>) {
        val features = context.lookup(Registries.CONFIGURED_FEATURE)

        context.register(
            OrePlacementSetup.COAL_VEIN_LOWER, PlacedFeature(
                features.getOrThrow(OreFeatureSetup.COAL_VEIN), commonOrePlacement(
                    30, HeightRangePlacement.triangle(
                        VerticalAnchor.absolute(-60), VerticalAnchor.absolute(12)
                    )
                )
            )
        )

        context.register(
            OrePlacementSetup.COAL_VEIN_UPPER, PlacedFeature(
                features.getOrThrow(OreFeatureSetup.COAL_VEIN), commonOrePlacement(
                    60, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(12), VerticalAnchor.top()
                    )
                )
            )
        )

        context.register(
            OrePlacementSetup.LARGE_COAL_VEIN, PlacedFeature(
                features.getOrThrow(OreFeatureSetup.LARGE_COAL_VEIN), rareOrePlacement(
                    10, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-20), VerticalAnchor.absolute(48)
                    )
                )
            )
        )
    }

    private fun orePlacement(
        countPlacement: PlacementModifier,
        heightRange: PlacementModifier
    ): List<PlacementModifier> {
        return listOf(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome())
    }

    private fun commonOrePlacement(count: Int, heightRange: PlacementModifier): List<PlacementModifier> {
        return orePlacement(CountPlacement.of(count), heightRange)
    }

    private fun rareOrePlacement(chance: Int, heightRange: PlacementModifier): List<PlacementModifier> {
        return orePlacement(RarityFilter.onAverageOnceEvery(chance), heightRange)
    }

    private fun seagrassPlacement(min: Int, max: Int): List<PlacementModifier> {
        return listOf(
            CountPlacement.of(BiasedToBottomInt.of(min, max)),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(BlockPos.ZERO, Blocks.WATER)),
            BiomeFilter.biome()
        )
    }
}