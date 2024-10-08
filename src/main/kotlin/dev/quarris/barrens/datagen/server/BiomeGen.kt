package dev.quarris.barrens.datagen.server

import dev.quarris.barrens.setup.BiomeSetup
import dev.quarris.barrens.worldgen.BiomeGenerators
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.world.level.biome.Biome

object BiomeGen : RegistryBootstrap<Biome> {
    override fun run(context: BootstapContext<Biome>) {
        context.register(
            BiomeSetup.Wasteland, BiomeGenerators.wasteland(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER)
            )
        )

        context.register(
            BiomeSetup.WastelandShore, BiomeGenerators.wastelandCoast(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER)
            )
        )

        context.register(
            BiomeSetup.DeadOcean, BiomeGenerators.deadOcean(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER)
            )
        )
    }
}