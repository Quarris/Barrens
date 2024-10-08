package dev.quarris.barrens.datagen.server

import dev.quarris.barrens.setup.*
import net.minecraft.core.BlockPos
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.util.valueproviders.BiasedToBottomInt
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.placement.*
import net.minecraft.world.level.material.Fluids

object PlacedFeatureGen : RegistryBootstrap<PlacedFeature> {
    override fun run(context: BootstapContext<PlacedFeature>) {
        val features = context.lookup(Registries.CONFIGURED_FEATURE)

        val deadWoodFeature = features.getOrThrow(ConfiguredFeatureSetup.DeadOakTree)
        val slateBoulderFeature = features.getOrThrow(ConfiguredFeatureSetup.SlateBoulder)
        val driedGrass = features.getOrThrow(ConfiguredFeatureSetup.DriedGrass)
        val singleDriedGrass = features.getOrThrow(ConfiguredFeatureSetup.SingleDriedGrass)
        val driedWaterLake = features.getOrThrow(ConfiguredFeatureSetup.DriedWaterLake)
        val deadSeagrass = features.getOrThrow(ConfiguredFeatureSetup.DeadSeagrass)
        val driedSandDisk = features.getOrThrow(ConfiguredFeatureSetup.DriedSandDisk)
        val gravelDisk = features.getOrThrow(ConfiguredFeatureSetup.GravelDisk)

        registerOres(context)

        context.register(
            PlacedFeatureSetup.DeadOakTree, PlacedFeature(
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
            PlacedFeatureSetup.SlateBoulder, PlacedFeature(
                slateBoulderFeature, listOf(
                    RarityFilter.onAverageOnceEvery(14),
                    InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP,
                    BiomeFilter.biome()
                )
            )
        )
        context.register(
            PlacedFeatureSetup.DriedGrassPatch, PlacedFeature(
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
            PlacedFeatureSetup.DriedGrassBonemeal, PlacedFeature(singleDriedGrass, listOf(PlacementUtils.isEmpty()))
        )

        context.register(
            PlacedFeatureSetup.DriedWaterLake, PlacedFeature(
                driedWaterLake, listOf(
                    RarityFilter.onAverageOnceEvery(20),
                    PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                    BiomeFilter.biome()
                )
            )
        )

        context.register(
            PlacedFeatureSetup.DeadSeagrassPatch, PlacedFeature(
                deadSeagrass, seagrassPlacement(20, 50)
            )
        )

        PlacementUtils.register(
            context,
            PlacedFeatureSetup.DriedSandDisk,
            driedSandDisk,
            CountPlacement.of(3),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
            BiomeFilter.biome()
        )

        PlacementUtils.register(
            context,
            PlacedFeatureSetup.GravelDisk,
            gravelDisk,
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
            BiomeFilter.biome()
        )
    }

    private fun registerOres(context: BootstapContext<PlacedFeature>) {
        val features = context.lookup(Registries.CONFIGURED_FEATURE)

        context.register(
            OrePlacementSetup.CoalVeinLower, PlacedFeature(
                features.getOrThrow(OreFeatureSetup.CoalVein), commonOrePlacement(
                    30, HeightRangePlacement.triangle(
                        VerticalAnchor.absolute(-60), VerticalAnchor.absolute(12)
                    )
                )
            )
        )

        context.register(
            OrePlacementSetup.CoalVeinUpper, PlacedFeature(
                features.getOrThrow(OreFeatureSetup.CoalVein), commonOrePlacement(
                    60, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(12), VerticalAnchor.top()
                    )
                )
            )
        )

        context.register(
            OrePlacementSetup.LargeCoalVein, PlacedFeature(
                features.getOrThrow(OreFeatureSetup.LargeCoalVein), rareOrePlacement(
                    10, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-20), VerticalAnchor.absolute(48)
                    )
                )
            )
        )

        PlacementUtils.register(
            context,
            PlacedFeatureSetup.PorousStoneUpper,
            features.getOrThrow(ConfiguredFeatureSetup.PorousStoneBlob),
            rareOrePlacement(
                6,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(128))
            )
        )
        PlacementUtils.register(
            context,
            PlacedFeatureSetup.PorousStoneLower,
            features.getOrThrow(ConfiguredFeatureSetup.PorousStoneBlob),
            commonOrePlacement(
                2,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(60))
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