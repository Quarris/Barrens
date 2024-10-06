package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object PlacedFeatureSetup {

    val DEAD_OAK_TREE = ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res("dead_oak_tree"))
    val SLATE_BOULDER = ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res("slate_boulder"))
    val DRIED_GRASS_PATCH = ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res("dried_grass_patch"))
    val DRIED_GRASS_BONEMEAL = ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res("single_dried_grass"))
}