package dev.quarris.wastelands.datagen.client

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.block.TallDeadSeagrassBlock
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.client.model.generators.ConfiguredModel
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile
import net.neoforged.neoforge.common.data.ExistingFileHelper

class BlockStateGen(output: PackOutput, exFileHelper: ExistingFileHelper) :
    BlockStateProvider(output, ModRef.ID, exFileHelper) {

    override fun registerStatesAndModels() {
        genLogs()
        genWood()

        BlockSetup.DriedDirt.get().let { block ->
            simpleRandomRotatedBlock(block)
        }

        BlockSetup.DriedShortGrass.get().let { block ->
            simpleBlock(
                block, models().cross(name(block), blockTexture(block))
                    .renderType(RenderType.CUTOUT.name)
            )
            generatedBlockItem(block)
        }

        BlockSetup.DeadSeagrass.get().let { block ->
            simpleBlock(
                block, models().cross(name(block), blockTexture(block))
                    .renderType(RenderType.CUTOUT.name)
            )
            generatedBlockItem(block)
        }

        BlockSetup.TallDeadSeagrass.get().let { block ->
            getVariantBuilder(block)
                .forAllStates { state ->
                    val half = state.getValue(TallDeadSeagrassBlock.Half)
                    val texture = blockTexture(block).withSuffix(if (half == DoubleBlockHalf.UPPER) "_top" else "_bottom")
                    return@forAllStates ConfiguredModel.builder().modelFile(models().cross(texture.path, texture).renderType(RenderType.CUTOUT.name)).build()
                }
        }
    }

    private fun genWood() {
        BlockSetup.DeadOakPlanks.get().let { block ->
            simpleBlockWithItem(block, cubeAll(block))
        }

        val deadPlanksTexture = blockTexture(BlockSetup.DeadOakPlanks.get())
        BlockSetup.DeadOakSlab.get().let { block ->
            slabBlock(block, deadPlanksTexture, deadPlanksTexture)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }
        BlockSetup.DeadOakStairs.get().let { block ->
            stairsBlock(block, deadPlanksTexture)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }
        BlockSetup.DeadOakFence.get().let { block ->
            fenceBlock(block, deadPlanksTexture)
            simpleBlockItem(
                block,
                models().withExistingParent(name(block) + "_inventory", "block/fence_inventory")
                    .texture("texture", deadPlanksTexture)
            )
        }
        BlockSetup.DeadOakFenceGate.get().let { block ->
            fenceGateBlock(block, deadPlanksTexture)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }

        BlockSetup.DeadOakPressurePlate.get().let { block ->
            pressurePlateBlock(block, deadPlanksTexture)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }

        BlockSetup.DeadOakButton.get().let { block ->
            buttonBlock(block, deadPlanksTexture)
            simpleBlockItem(
                block,
                models().withExistingParent(name(block) + "_inventory", "block/button_inventory")
                    .texture("texture", deadPlanksTexture)
            )
        }

        BlockSetup.DeadOakDoor.get().let { block ->
            val texture = blockTexture(block)
            doorBlockWithRenderType(
                block,
                texture.withSuffix("_bottom"),
                texture.withSuffix("_top"),
                RenderType.CUTOUT.name
            )
            itemModels().basicItem(block.asItem())
        }

        BlockSetup.DeadOakTrapdoor.get().let { block ->
            val texture = blockTexture(block)
            trapdoorBlockWithRenderType(block, texture, true, RenderType.CUTOUT.name)
            simpleBlockItem(block, models().getExistingFile(key(block).withSuffix("_bottom")))
        }

        BlockSetup.Slate.get().let { block ->
            simpleRandomRotatedBlock(block)
        }
    }

    private fun genLogs() {
        BlockSetup.DeadOakLog.get().let { block ->
            logBlock(block)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }
        BlockSetup.CharredDeadOakLog.get().let { block ->
            logBlock(block)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }
        BlockSetup.StrippedDeakOakLog.get().let { block ->
            logBlock(block)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }
        BlockSetup.StrippedCharredDeadOakLog.get().let { block ->
            logBlock(block)
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }

        BlockSetup.DeadOakWood.get().let { block ->
            axisBlock(block, blockTexture(block), blockTexture(block))
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }

        BlockSetup.StrippedDeadOakWood.get().let { block ->
            axisBlock(block, blockTexture(block), blockTexture(block))
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }

        BlockSetup.CharredDeadOakWood.get().let { block ->
            axisBlock(block, blockTexture(block), blockTexture(block))
            simpleBlockItem(block, models().getExistingFile(key(block)))
        }

        BlockSetup.StrippedCharredDeadOakWood.get().let { block ->
            axisBlock(block, blockTexture(block), blockTexture(block))
            simpleBlockItem(block, models().getExistingFile(key(block)))
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

    private fun generatedBlockItem(block: Block): ItemModelBuilder {
        return itemModels().getBuilder(name(block))
            .parent(UncheckedModelFile("item/generated"))
            .texture("layer0", blockTexture(block))

    }

    private fun key(block: Block): ResourceLocation {
        return BuiltInRegistries.BLOCK.getKey(block)
    }

    private fun name(block: Block): String {
        return key(block).path
    }
}