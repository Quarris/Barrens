package dev.quarris.wastelands.datagen

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.datagen.client.BlockStateGen
import dev.quarris.wastelands.datagen.client.EnUsLangGen
import dev.quarris.wastelands.datagen.server.BiomeGen
import dev.quarris.wastelands.datagen.server.WorldPresetGen
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.data.DataProvider
import net.minecraft.world.level.dimension.LevelStem
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.data.event.GatherDataEvent


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object Datagen {

    @SubscribeEvent
    private fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val existingFileHelper = event.existingFileHelper
        val inputs = event.inputs
        val lookupProvider = event.lookupProvider

        val isServer = event.includeServer()
        val isClient = event.includeClient()

        generator.addProvider(isClient, DataProvider.Factory { output -> BlockStateGen(output, existingFileHelper) })
        generator.addProvider(isClient, ::EnUsLangGen)
        generator.addProvider(isServer, DataProvider.Factory { output ->
            DatapackBuiltinEntriesProvider(
                output,
                lookupProvider,
                RegistrySetBuilder()
                    .add(Registries.BIOME, BiomeGen)
                    .add(Registries.WORLD_PRESET, WorldPresetGen),
                setOf(ModRef.ID)
            )
        })
    }
}