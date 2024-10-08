package dev.quarris.wastelands.datagen

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.datagen.client.BlockStateGen
import dev.quarris.wastelands.datagen.client.EnUsLangGen
import dev.quarris.wastelands.datagen.client.ItemModelGen
import dev.quarris.wastelands.datagen.server.*
import dev.quarris.wastelands.datagen.server.loot.BlockLootProvider
import dev.quarris.wastelands.datagen.server.tags.BiomeTagGen
import dev.quarris.wastelands.datagen.server.tags.BlockTagGen
import dev.quarris.wastelands.datagen.server.tags.ItemTagGen
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.data.DataProvider
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object Datagen {

    @SubscribeEvent
     fun gatherData(event: GatherDataEvent) { val generator = event.generator
        val existingFileHelper = event.existingFileHelper
        val lookupProvider = event.lookupProvider

        val isServer = event.includeServer()
        val isClient = event.includeClient()

        // Client
        generator.addProvider(isClient, DataProvider.Factory { output -> BlockStateGen(output, existingFileHelper) })
        generator.addProvider(isClient, DataProvider.Factory { output -> ItemModelGen(output, existingFileHelper) })
        generator.addProvider(isClient, ::EnUsLangGen)

        // Server

        // Datapack Registries
        generator.addProvider(isServer, DataProvider.Factory { output ->
            DatapackBuiltinEntriesProvider(
                output,
                lookupProvider,
                RegistrySetBuilder()
                    .add(Registries.BIOME, BiomeGen)
                    .add(Registries.WORLD_PRESET, WorldPresetGen)
                    .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureGen)
                    .add(Registries.PLACED_FEATURE, PlacedFeatureGen)
                    .add(Registries.NOISE_SETTINGS, NoiseGeneratorGen),
                setOf(ModRef.ID)
            )
        })

        // Tags
        val blockTags = generator.addProvider(isServer, DataProvider.Factory {
            BlockTagGen(generator.packOutput, lookupProvider, existingFileHelper)
        })
        generator.addProvider(isServer, DataProvider.Factory { output ->
            ItemTagGen(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper)
        })
        generator.addProvider(isServer, DataProvider.Factory { output ->
            BiomeTagGen(output, lookupProvider, existingFileHelper)
        })

        // Loottables
        generator.addProvider(isServer, DataProvider.Factory { output ->
            LootTableProvider(
                output,
                setOf(),
                listOf(
                    LootTableProvider.SubProviderEntry(
                        ::BlockLootProvider,
                        LootContextParamSets.BLOCK
                    )
                ),
            )
        })

        // Recipes
        generator.addProvider(isServer, DataProvider.Factory { output ->
            RecipesGen(output)
        })
    }
}