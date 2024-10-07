package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object WorldPresetSetup {

    val WASTELANDS = ResourceKey.create(Registries.WORLD_PRESET, ModRef.res("wastelands"))
}