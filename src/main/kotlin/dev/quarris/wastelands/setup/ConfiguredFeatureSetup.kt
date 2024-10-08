package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object ConfiguredFeatureSetup {

    val DeadOakTree = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("dead_oak_tree"))
    val SlateBoulder = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("slate_boulder"))
    val DriedGrass = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("dried_grass"))
    val SingleDriedGrass = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("single_dried_grass"))
    val DriedDirtWaterLake = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("water_lake"))
    val DeadSeagrassSingle = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("dead_seagrass_single"))
    val DeadSeagrass = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("dead_seagrass"))
}