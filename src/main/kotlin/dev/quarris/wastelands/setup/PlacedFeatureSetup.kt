package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object PlacedFeatureSetup {

    val DeadOakTree = register("dead_oak_tree")
    val SlateBoulder = register("slate_boulder")
    val DriedGrassPatch = register("dried_grass_patch")
    val DriedGrassBonemeal = register("single_dried_grass")
    val DriedWaterLake = register("dried_water_lake")
    val DeadSeagrassPatch = register("dead_seagrass_patch")
    val DriedSandDisk = register("dried_sand_disk")
    val GravelDisk = register("gravel_disk")
    val PorousStoneUpper = register("porous_stone_upper")
    val PorousStoneLower = register("porous_stone_lower")

    private fun register(name: String) : ResourceKey<PlacedFeature> {
        return ResourceKey.create(Registries.PLACED_FEATURE, ModRef.res(name))
    }

}