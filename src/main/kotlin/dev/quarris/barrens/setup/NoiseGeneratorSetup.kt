package dev.quarris.barrens.setup

import dev.quarris.barrens.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object NoiseGeneratorSetup {

    val Barrens = ResourceKey.create(Registries.NOISE_SETTINGS, ModRef.res("barrens"))

}