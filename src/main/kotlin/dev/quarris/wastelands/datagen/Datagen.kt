package dev.quarris.wastelands.datagen

import dev.quarris.wastelands.ModRef
import net.minecraft.core.Holder
import net.minecraft.core.HolderGetter
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.data.DataProvider
import net.minecraft.data.worldgen.biome.NetherBiomes
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.MultiNoiseBiomeSource
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists
import net.minecraft.world.level.biome.TheEndBiomeSource
import net.minecraft.world.level.dimension.BuiltinDimensionTypes
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings
import net.minecraft.world.level.levelgen.presets.WorldPreset
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.data.event.GatherDataEvent


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object Datagen {

    var stem: LevelStem? = null

    @SubscribeEvent
    private fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val existingFileHelper = event.existingFileHelper
        val inputs = event.inputs
        val lookupProvider = event.lookupProvider

        val isServer = event.includeServer()
        val isClient = event.includeClient()

        generator.addProvider(isServer, DataProvider.Factory { output ->
            DatapackBuiltinEntriesProvider(
                output,
                lookupProvider,
                RegistrySetBuilder()
                    .add(Registries.BIOME) { ctx ->
                        ctx.register(
                            ResourceKey.create(Registries.BIOME, ModRef.res("wasteland")),
                            NetherBiomes.basaltDeltas(
                                ctx.lookup(Registries.PLACED_FEATURE),
                                ctx.lookup(Registries.CONFIGURED_CARVER)
                            )
                        )
                    }
                    .add(Registries.LEVEL_STEM) { ctx ->

                        val dimensions: HolderGetter<DimensionType> = ctx.lookup(Registries.DIMENSION_TYPE)
                        val presets: HolderGetter<MultiNoiseBiomeSourceParameterList> =
                            ctx.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
                        val noiseSettings = ctx.lookup(Registries.NOISE_SETTINGS)
                        stem = LevelStem(
                            dimensions.getOrThrow(BuiltinDimensionTypes.OVERWORLD), NoiseBasedChunkGenerator(
                                MultiNoiseBiomeSource.createFromPreset(
                                    presets.getOrThrow(
                                        MultiNoiseBiomeSourceParameterLists.OVERWORLD
                                    )
                                ),
                                noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
                            )
                        )
                        ctx.register(
                            ResourceKey.create(Registries.LEVEL_STEM, ModRef.res("wastelands")),
                            stem
                        )
                    }
                    .add(Registries.WORLD_PRESET) { ctx ->

                        val dimensionTypes: HolderGetter<DimensionType> = ctx.lookup(Registries.DIMENSION_TYPE)
                        val biomes = ctx.lookup(Registries.BIOME)
                        val noiseSettings = ctx.lookup(Registries.NOISE_SETTINGS)
                        val endGenSettings: Holder<NoiseGeneratorSettings> =
                            noiseSettings.getOrThrow(NoiseGeneratorSettings.END)
                        ctx.register(
                            ResourceKey.create(Registries.WORLD_PRESET, ModRef.res("wastelands")), WorldPreset(
                                mapOf(
                                    Pair(
                                        LevelStem.OVERWORLD, LevelStem(
                                            dimensionTypes.getOrThrow(BuiltinDimensionTypes.END),
                                            NoiseBasedChunkGenerator(TheEndBiomeSource.create(biomes), endGenSettings)
                                        )
                                    )
                                )
                            )
                        )
                    },
                setOf(ModRef.ID)
            )
        })
    }
}