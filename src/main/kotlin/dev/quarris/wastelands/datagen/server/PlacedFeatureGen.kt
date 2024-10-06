package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.ConfiguredFeatureSetup
import dev.quarris.wastelands.setup.PlacedFeatureSetup
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.util.valueproviders.BiasedToBottomInt
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.levelgen.placement.BiomeFilter
import net.minecraft.world.level.levelgen.placement.CountPlacement
import net.minecraft.world.level.levelgen.placement.InSquarePlacement
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraft.world.level.levelgen.placement.RarityFilter
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator

object PlacedFeatureGen : RegistryBootstrap<PlacedFeature> {
    override fun run(context: BootstrapContext<PlacedFeature>) {
        val features = context.lookup(Registries.CONFIGURED_FEATURE)

        val deadWoodFeature = features.getOrThrow(ConfiguredFeatureSetup.DEAD_OAK_TREE)
        val slateBoulderFeature = features.getOrThrow(ConfiguredFeatureSetup.SLATE_BOULDER)

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
    }
}