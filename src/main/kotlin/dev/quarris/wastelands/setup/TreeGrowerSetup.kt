package dev.quarris.wastelands.setup

import net.minecraft.resources.ResourceKey
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import java.util.*

object TreeGrowerSetup {

    val DeadOak = object : AbstractTreeGrower() {
        override fun getConfiguredFeature(pRandom: RandomSource, pHasFlowers: Boolean): ResourceKey<ConfiguredFeature<*, *>>? {
            return ConfiguredFeatureSetup.DeadOakTree
        }
    }

}