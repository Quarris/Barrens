package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome

object BiomeSetup {

    val WASTELAND: ResourceKey<Biome> = ResourceKey.create(Registries.BIOME, ModRef.res("wasteland"))
    val DEAD_OCEAN: ResourceKey<Biome> = ResourceKey.create(Registries.BIOME, ModRef.res("dead_ocean"))

}