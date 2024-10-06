package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.ConfiguredFeatureSetup
import dev.quarris.wastelands.setup.FeatureSetup
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.LakeFeature
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

object ConfiguredFeatureGen : RegistrySetBuilder.RegistryBootstrap<ConfiguredFeature<*, *>> {
    override fun run(context: BootstrapContext<ConfiguredFeature<*, *>>) {
        context.register(
            ConfiguredFeatureSetup.DEAD_OAK_TREE,
            ConfiguredFeature(FeatureSetup.DEAD_OAK_TREE.get(), ProbabilityFeatureConfiguration(0.7f))
        )
        context.register(
            ConfiguredFeatureSetup.SLATE_BOULDER,
            ConfiguredFeature(
                FeatureSetup.BOULDER.get(),
                BlockStateConfiguration(BlockSetup.SLATE.get().defaultBlockState())
            )
        )
        context.register(
            ConfiguredFeatureSetup.DRIED_GRASS,
            ConfiguredFeature(
                Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(
                    12,
                    PlacementUtils.onlyWhenEmpty<SimpleBlockConfiguration, Feature<SimpleBlockConfiguration>>(
                        Feature.SIMPLE_BLOCK,
                        SimpleBlockConfiguration(BlockStateProvider.simple(BlockSetup.DRIED_SHORT_GRASS.get()))
                    )
                )
            )
        )

        context.register(
            ConfiguredFeatureSetup.SINGLE_DRIED_GRASS,
            ConfiguredFeature(
                Feature.SIMPLE_BLOCK,
                SimpleBlockConfiguration(BlockStateProvider.simple(BlockSetup.DRIED_SHORT_GRASS.get().defaultBlockState()))
            )
        )

        context.register(
            ConfiguredFeatureSetup.DRIED_DIRT_WATER_LAKE,
            ConfiguredFeature(
                Feature.LAKE,
                LakeFeature.Configuration(
                    BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
                    BlockStateProvider.simple(BlockSetup.DRIED_DIRT.get().defaultBlockState())
                )
            )
        )
    }


}