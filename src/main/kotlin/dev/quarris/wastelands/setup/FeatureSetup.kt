package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.worldgen.feature.DeadWoodFeature
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import java.util.function.Supplier

object FeatureSetup {

    val REGISTRY: DeferredRegister<Feature<*>> = DeferredRegister.create(Registries.FEATURE, ModRef.ID)

    val DEAD_WOOD = REGISTRY.register("dead_wood", Supplier { DeadWoodFeature(ProbabilityFeatureConfiguration.CODEC) })

    fun init() {
        REGISTRY.register(MOD_BUS)
    }

}