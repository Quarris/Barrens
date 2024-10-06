package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object PlacedFeatureSetup {

    val DEAD_OAK_TREE = ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res("dead_oak_tree"))
    val SLATE_BOULDER = ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res("slate_boulder"))
}