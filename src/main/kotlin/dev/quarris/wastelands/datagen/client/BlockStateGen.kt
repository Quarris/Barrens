package dev.quarris.wastelands.datagen.client

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.client.renderer.RenderType
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
        genLogs()
        genWood()

        BlockSetup.DRIED_DIRT.get().let { sand ->
            simpleRandomRotatedBlock(sand)
        }
    }

    private fun genWood() {
        BlockSetup.DEAD_OAK_PLANKS.get().let { planks ->
            simpleBlockWithItem(planks, cubeAll(planks))
        }

        val deadPlanksTexture = blockTexture(BlockSetup.DEAD_OAK_PLANKS.get())
        BlockSetup.DEAD_OAK_SLAB.get().let { block ->
            slabBlock(block, deadPlanksTexture, deadPlanksTexture)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }
        BlockSetup.DEAD_OAK_STAIRS.get().let { block ->
            stairsBlock(block, deadPlanksTexture)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }
        BlockSetup.DEAD_OAK_FENCE.get().let { block ->
            fenceBlock(block, deadPlanksTexture)
            simpleBlockItem(
                block,
                models().withExistingParent(name(block) + "_inventory", "block/fence_inventory")
                    .texture("texture", deadPlanksTexture)
            )
        }
        BlockSetup.DEAD_OAK_FENCE_GATE.get().let { block ->
            fenceGateBlock(block, deadPlanksTexture)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }

        BlockSetup.DEAD_OAK_PRESSURE_PLATE.get().let { block ->
            pressurePlateBlock(block, deadPlanksTexture)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }

        BlockSetup.DEAD_OAK_BUTTON.get().let { block ->
            buttonBlock(block, deadPlanksTexture)
            simpleBlockItem(
                block,
                models().withExistingParent(name(block) + "_inventory", "block/button_inventory")
                    .texture("texture", deadPlanksTexture)
            )
        }

        BlockSetup.DEAD_OAK_DOOR.get().let { block ->
            val texture = blockTexture(block)
            doorBlockWithRenderType(block, texture.withSuffix("_bottom"), texture.withSuffix("_top"), RenderType.CUTOUT.name)
            itemModels().basicItem(block.asItem())
        }

        BlockSetup.DEAD_OAK_TRAPDOOR.get().let { block ->
            val texture = blockTexture(block)
            trapdoorBlockWithRenderType(block, texture, true, RenderType.CUTOUT.name)
            simpleBlockItem(block, models().getExistingFile(key(block).withSuffix("_bottom")))
        }

        BlockSetup.SLATE.get().let { block ->
            simpleRandomRotatedBlock(block)
        }
    }

    private fun genLogs() {
        BlockSetup.DEAD_OAK_LOG.get().let { log ->
            logBlock(log)
            simpleBlockItem(log, models().getExistingFile(key(log)))
        }
        BlockSetup.CHARRED_DEAD_OAK_LOG.get().let { log ->
            logBlock(log)
            simpleBlockItem(log, models().getExistingFile(key(log)))
        }
        BlockSetup.STRIPPED_DEAD_OAK_LOG.get().let { log ->
            logBlock(log)
            simpleBlockItem(log, models().getExistingFile(key(log)))
        }
        BlockSetup.STRIPPED_CHARRED_DEAD_OAK_LOG.get().let { log ->
            logBlock(log)
            simpleBlockItem(log, models().getExistingFile(key(log)))
        }

        BlockSetup.DEAD_OAK_WOOD.get().let { log ->
            axisBlock(log, blockTexture(log), blockTexture(log))
            simpleBlockItem(log, models().getExistingFile(key(log)))
        }

        BlockSetup.STRIPPED_DEAD_OAK_WOOD.get().let { log ->
            axisBlock(log, blockTexture(log), blockTexture(log))
            simpleBlockItem(log, models().getExistingFile(key(log)))
        }

        BlockSetup.CHARRED_DEAD_OAK_WOOD.get().let { log ->
            axisBlock(log, blockTexture(log), blockTexture(log))
            simpleBlockItem(log, models().getExistingFile(key(log)))
        }

        BlockSetup.STRIPPED_CHARRED_DEAD_OAK_WOOD.get().let { log ->
            axisBlock(log, blockTexture(log), blockTexture(log))
            simpleBlockItem(log, models().getExistingFile(key(log)))
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