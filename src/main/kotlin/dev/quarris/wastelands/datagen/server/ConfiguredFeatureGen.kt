package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.ConfiguredFeatureSetup
import dev.quarris.wastelands.setup.FeatureSetup
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration

object ConfiguredFeatureGen : RegistrySetBuilder.RegistryBootstrap<ConfiguredFeature<*, *>> {
    override fun run(context: BootstrapContext<ConfiguredFeature<*, *>>) {
        context.register(ConfiguredFeatureSetup.DEAD_WOOD, ConfiguredFeature(FeatureSetup.DEAD_WOOD.get(), ProbabilityFeatureConfiguration(1.0f)))
    }


}