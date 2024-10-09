package dev.quarris.barrens.datagen.server

import dev.quarris.barrens.setup.NoiseGeneratorSetup
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.data.worldgen.SurfaceRuleData
import net.minecraft.world.level.biome.OverworldBiomeBuilder
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.*

object NoiseGeneratorGen : NoiseRouterData(), RegistryBootstrap<NoiseGeneratorSettings> {

    private val BarrensNoiseSettings = NoiseSettings.create(-64, 384, 1, 4)

    override fun run(context: BootstapContext<NoiseGeneratorSettings>) {
        val densityFunctions = context.lookup(Registries.DENSITY_FUNCTION)
        val noiseParams = context.lookup(Registries.NOISE)
        val overworldRouter = overworld(densityFunctions, noiseParams, false, false)
        val barrensRouter = NoiseRouter(
            overworldRouter.barrierNoise,
            DensityFunctions.constant(-1.0),
            DensityFunctions.constant(0.0),
            overworldRouter.lavaNoise,
            overworldRouter.temperature,
            overworldRouter.vegetation,
            overworldRouter.continents,
            overworldRouter.erosion,
            overworldRouter.depth,
            overworldRouter.ridges,
            overworldRouter.initialDensityWithoutJaggedness,
            overworldRouter.finalDensity,
            overworldRouter.veinToggle,
            overworldRouter.veinRidged,
            overworldRouter.veinGap
        )
        context.register(
            NoiseGeneratorSetup.Barrens,
            NoiseGeneratorSettings(
                BarrensNoiseSettings,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                barrensRouter,
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