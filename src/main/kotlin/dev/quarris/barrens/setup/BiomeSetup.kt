package dev.quarris.barrens.setup

import dev.quarris.barrens.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome

object BiomeSetup {

    val Wasteland: ResourceKey<Biome> = ResourceKey.create(Registries.BIOME, ModRef.res("wasteland"))
    val WastelandShore: ResourceKey<Biome> = ResourceKey.create(Registries.BIOME, ModRef.res("wasteland_shore"))
    val DeadOcean: ResourceKey<Biome> = ResourceKey.create(Registries.BIOME, ModRef.res("dead_ocean"))

}