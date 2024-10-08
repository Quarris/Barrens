package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.worldgen.feature.BoulderFeature
import dev.quarris.wastelands.worldgen.feature.DeadOakTreeFeature
import dev.quarris.wastelands.worldgen.feature.DeadSeagrassFeature
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import java.util.function.Supplier

object FeatureSetup {

    val REGISTRY: DeferredRegister<Feature<*>> = DeferredRegister.create(Registries.FEATURE, ModRef.ID)

    val DEAD_OAK_TREE = REGISTRY.register("dead_oak_tree", Supplier { DeadOakTreeFeature(ProbabilityFeatureConfiguration.CODEC) })
    val BOULDER = REGISTRY.register("boulder", Supplier { BoulderFeature(BlockStateConfiguration.CODEC) })
    val DEAD_SEAGRASS = REGISTRY.register("dead_seagrass", Supplier { DeadSeagrassFeature(ProbabilityFeatureConfiguration.CODEC) })

    fun init() {
        REGISTRY.register(MOD_BUS)
    }

}