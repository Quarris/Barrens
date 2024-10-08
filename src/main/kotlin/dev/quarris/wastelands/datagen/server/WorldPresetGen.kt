package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.BiomeSetup
import dev.quarris.wastelands.setup.NoiseGeneratorSetup
import dev.quarris.wastelands.setup.WorldPresetSetup
import net.minecraft.core.Holder
import net.minecraft.core.HolderGetter
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.level.biome.*
import net.minecraft.world.level.biome.Climate.ParameterPoint
import net.minecraft.world.level.dimension.BuiltinDimensionTypes
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings
import net.minecraft.world.level.levelgen.presets.WorldPreset
import terrablender.api.ParameterUtils.*
import kotlin.Pair
import com.mojang.datafixers.util.Pair as DataPair

object WorldPresetGen : RegistryBootstrap<WorldPreset> {

    private lateinit var netherStem: LevelStem
    private lateinit var endStem: LevelStem
    private lateinit var wastelandsStem: LevelStem

    override fun run(context: BootstrapContext<WorldPreset>) {
        val biomeSources = context.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
        val dimensionTypes: HolderGetter<DimensionType> = context.lookup(Registries.DIMENSION_TYPE)
        val biomes = context.lookup(Registries.BIOME)
        val noiseSettings = context.lookup(Registries.NOISE_SETTINGS)
        val wastelandSettings: Holder<NoiseGeneratorSettings> = noiseSettings.getOrThrow(NoiseGeneratorSetup.Wastelands)

        netherStem = LevelStem(
            dimensionTypes.getOrThrow(BuiltinDimensionTypes.NETHER),
            NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromPreset(
                    biomeSources.getOrThrow(
                        MultiNoiseBiomeSourceParameterLists.NETHER
                    )
                ), noiseSettings.getOrThrow(NoiseGeneratorSettings.NETHER)
            )
        )

        endStem = LevelStem(
            dimensionTypes.getOrThrow(BuiltinDimensionTypes.END),
            NoiseBasedChunkGenerator(
                TheEndBiomeSource.create(biomes),
                noiseSettings.getOrThrow(NoiseGeneratorSettings.END)
            )
        )

        val wastelandsPoints = ParameterPointListBuilder()
            .temperature(Temperature.FULL_RANGE)
            .humidity(Humidity.FULL_RANGE)
            .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
            .erosion(Erosion.FULL_RANGE)
            .depth(Depth.FULL_RANGE)
            .weirdness(Weirdness.FULL_RANGE)
            .build()

        val coastPoints = ParameterPointListBuilder()
            .temperature(Temperature.FULL_RANGE)
            .humidity(Humidity.FULL_RANGE)
            .continentalness(Continentalness.span(Continentalness.COAST, Continentalness.NEAR_INLAND))
            .erosion(Erosion.FULL_RANGE)
            .depth(Depth.FULL_RANGE)
            .weirdness(
                Weirdness.LOW_SLICE_VARIANT_ASCENDING,
                Weirdness.LOW_SLICE_NORMAL_DESCENDING,
                Weirdness.MID_SLICE_NORMAL_ASCENDING,
                Weirdness.MID_SLICE_NORMAL_DESCENDING,
                Weirdness.MID_SLICE_VARIANT_ASCENDING,
                Weirdness.MID_SLICE_VARIANT_DESCENDING,
            )
            .build()

        val oceanPoints = ParameterPointListBuilder()
            .temperature(Temperature.FULL_RANGE)
            .humidity(Humidity.FULL_RANGE)
            .continentalness(Continentalness.span(Continentalness.DEEP_OCEAN, Continentalness.OCEAN))
            .erosion(Erosion.FULL_RANGE)
            .depth(Depth.FULL_RANGE)
            .weirdness(Weirdness.FULL_RANGE)
            .build()

        val parameterList =
            wastelandsPoints.map { point: ParameterPoint ->
                DataPair.of<ParameterPoint, Holder<Biome>>(
                    point,
                    biomes.getOrThrow(BiomeSetup.Wasteland)
                )
            }.plus(
                coastPoints.map { point: ParameterPoint ->
                    DataPair.of<ParameterPoint, Holder<Biome>>(
                        point,
                        biomes.getOrThrow(BiomeSetup.WastelandShore)
                    )
                }
            ).plus(
                oceanPoints.map { point: ParameterPoint ->
                    DataPair.of<ParameterPoint, Holder<Biome>>(
                        point,
                        biomes.getOrThrow(BiomeSetup.DeadOcean)
                    )
                }
            ).toList()



        wastelandsStem = LevelStem(
            dimensionTypes.getOrThrow(BuiltinDimensionTypes.OVERWORLD),
            NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                    Climate.ParameterList(parameterList)
                ), wastelandSettings
            )
        )

        context.register(
            WorldPresetSetup.Wastelands,
            WorldPreset(
                mapOf(
                    Pair(LevelStem.OVERWORLD, wastelandsStem),
                    Pair(LevelStem.NETHER, netherStem),
                    Pair(LevelStem.END, endStem)
                )
            )
        )
    }
}