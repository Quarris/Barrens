package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object NoiseGeneratorSetup {

    val WASTELANDS = ResourceKey.create(Registries.NOISE_SETTINGS, ModRef.res("wastelands"))

}