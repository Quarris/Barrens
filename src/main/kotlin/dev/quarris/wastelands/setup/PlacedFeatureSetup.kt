package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object PlacedFeatureSetup {

    val DEAD_OAK_TREE = register("dead_oak_tree")
    val SLATE_BOULDER = register("slate_boulder")
    val DRIED_GRASS_PATCH = register("dried_grass_patch")
    val DRIED_GRASS_BONEMEAL = register("single_dried_grass")
    val DRIED_DIRT_WATER_LAKE = register("dried_dirt_water_lake")
    val DEAD_SEAGRASS_PATCH = register("dead_seagrass_patch")
    val DEAD_SEAGRASS_BONEMEAL = register("dead_seagrass_bonemeal")

    private fun register(name: String) : ResourceKey<PlacedFeature> {
        return ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res(name))
    }

}