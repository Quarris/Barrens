package dev.quarris.wastelands.datagen.client

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.client.model.generators.ConfiguredModel
import net.neoforged.neoforge.common.data.ExistingFileHelper

class BlockStateGen(output: PackOutput, exFileHelper: ExistingFileHelper) :
    BlockStateProvider(output, ModRef.ID, exFileHelper) {

    override fun registerStatesAndModels() {
        BlockSetup.DRIED_DIRT.get().let { sand ->
            simpleRandomRotatedBlock(sand)
        }

        BlockSetup.DEAD_WOOD_LOG.get().let { log ->
            simpleBlockWithItem(log, models().cubeColumn(name(log), blockTexture(log), blockTexture(log).withSuffix("_top")))
        }
        BlockSetup.CHARRED_DEAD_WOOD_LOG.get().let { log ->
            simpleBlockWithItem(log, models().cubeColumn(name(log), blockTexture(log), blockTexture(log).withSuffix("_top")))
        }
        BlockSetup.STRIPPED_DEAD_WOOD_LOG.get().let { log ->
            simpleBlockWithItem(log, models().cubeColumn(name(log), blockTexture(log), blockTexture(log).withSuffix("_top")))
        }
        BlockSetup.STRIPPED_CHARRED_DEAD_WOOD_LOG.get().let { log ->
            simpleBlockWithItem(log, models().cubeColumn(name(log), blockTexture(log), blockTexture(log).withSuffix("_top")))
        }
    }

    private fun simpleRandomRotatedBlock(block: Block, withItem: Boolean = true) {
        val model = cubeAll(block)
        getVariantBuilder(block)
            .partialState().addModels(
                ConfiguredModel(model, 0, 0, false),
                ConfiguredModel(model, 0, 90, false),
                ConfiguredModel(model, 0, 180, false),
                ConfiguredModel(model, 0, 270, false),
            )


        if (withItem) {
            simpleBlockItem(block, model)
        }
    }

    private fun key(block: Block): ResourceLocation {
        return BuiltInRegistries.BLOCK.getKey(block)
    }

    private fun name(block: Block): String {
        return key(block).path
    }
}