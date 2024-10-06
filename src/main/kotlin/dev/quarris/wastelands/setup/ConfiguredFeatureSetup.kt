package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object ConfiguredFeatureSetup {

    val DEAD_OAK_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("dead_oak_tree"))
    val SLATE_BOULDER = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("slate_boulder"))
    val DRIED_GRASS = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("dried_grass"))
    val SINGLE_DRIED_GRASS = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("single_dried_grass"))
    val DRIED_DIRT_WATER_LAKE = ResourceKey.create(Registries.CONFIGURED_FEATURE, ModRef.res("water_lake"))
}