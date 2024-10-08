package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.worldgen.feature.BoulderFeature
import dev.quarris.wastelands.worldgen.feature.DeadOakTreeFeature
import dev.quarris.wastelands.worldgen.feature.DeadSeagrassFeature
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration
import net.minecraftforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import java.util.function.Supplier

object FeatureSetup {

    val Registry: DeferredRegister<Feature<*>> = DeferredRegister.create(Registries.FEATURE, ModRef.ID)

    val DeadOakTree = Registry.register("dead_oak_tree", Supplier { DeadOakTreeFeature(ProbabilityFeatureConfiguration.CODEC) })
    val Boulder = Registry.register("boulder", Supplier { BoulderFeature(BlockStateConfiguration.CODEC) })
    val DeadSeagrass = Registry.register("dead_seagrass", Supplier { DeadSeagrassFeature(ProbabilityFeatureConfiguration.CODEC) })

    fun init() {
        Registry.register(MOD_BUS)
    }

}