package dev.quarris.barrens.setup

import dev.quarris.barrens.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object WorldPresetSetup {

    val Barrens = ResourceKey.create(Registries.WORLD_PRESET, ModRef.res("barrens"))
}