package dev.quarris.barrens.datagen.server

import dev.quarris.barrens.setup.NoiseGeneratorSetup
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.data.worldgen.SurfaceRuleData
import net.minecraft.world.level.biome.OverworldBiomeBuilder
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings
import net.minecraft.world.level.levelgen.NoiseRouterData
import net.minecraft.world.level.levelgen.NoiseSettings

object NoiseGeneratorGen : NoiseRouterData(), RegistryBootstrap<NoiseGeneratorSettings> {

    private val BarrensNoiseSettings = NoiseSettings.create(-64, 384, 1, 4)

    override fun run(context: BootstapContext<NoiseGeneratorSettings>) {
        val densityFunctions = context.lookup(Registries.DENSITY_FUNCTION)
        val noiseParams = context.lookup(Registries.NOISE)
        context.register(
            NoiseGeneratorSetup.Barrens,
            NoiseGeneratorSettings(
                BarrensNoiseSettings,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                overworld(densityFunctions, noiseParams, false, false),
                SurfaceRuleData.overworld(),
                OverworldBiomeBuilder().spawnTarget(),
                63,
                false,
                true,
                false,
                false
            )
        )
    }
}