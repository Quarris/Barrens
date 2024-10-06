package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.ConfiguredFeatureSetup
import dev.quarris.wastelands.setup.FeatureSetup
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.level.levelgen.feature.BlockBlobFeature
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration

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
    }


}