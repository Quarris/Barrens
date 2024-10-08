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

        val deadWoodFeature = features.getOrThrow(ConfiguredFeatureSetup.DeadOakTree)
        val slateBoulderFeature = features.getOrThrow(ConfiguredFeatureSetup.SlateBoulder)
        val driedGrass = features.getOrThrow(ConfiguredFeatureSetup.DriedGrass)
        val singleDriedGrass = features.getOrThrow(ConfiguredFeatureSetup.SingleDriedGrass)
        val waterLake = features.getOrThrow(ConfiguredFeatureSetup.DriedDirtWaterLake)
        val deadSeagrassSingle = features.getOrThrow(ConfiguredFeatureSetup.DeadSeagrassSingle)
        val deadSeagrass = features.getOrThrow(ConfiguredFeatureSetup.DeadSeagrass)

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
            PlacedFeatureSetup.DriedDirtWaterLake, PlacedFeature(
                waterLake, listOf(
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
    }

    private fun registerOres(context: BootstrapContext<PlacedFeature>) {
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