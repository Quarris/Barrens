package dev.quarris.barrens.datagen.server.tags

import dev.quarris.barrens.ModRef
import dev.quarris.barrens.setup.BlockSetup
import dev.quarris.barrens.setup.TagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.tags.BlockTags
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.BlockTagsProvider
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class BlockTagGen(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper
) : BlockTagsProvider(output, lookupProvider, ModRef.ID, existingFileHelper) {

    override fun addTags(provider: HolderLookup.Provider) {
        miningTags()
        miscTags()
    }

    private fun miscTags() {
        tag(BlockTags.SAND).add(BlockSetup.DriedSand.get())
        tag(Tags.Blocks.SANDSTONE).add(BlockSetup.DriedSandstone.get())

        BlockSetup.DriedShortGrass.get().let { block ->
            tag(BlockTags.REPLACEABLE).add(block)
            tag(BlockTags.REPLACEABLE_BY_TREES).add(block)
        }

        tag(TagSetup.Blocks.DeadOakLogs).add(
            BlockSetup.DeadOakWood.get(),
            BlockSetup.StrippedDeakOakLog.get(),
            BlockSetup.StrippedDeadOakWood.get(),
            BlockSetup.CharredDeadOakLog.get(),
            BlockSetup.CharredDeadOakWood.get(),
            BlockSetup.StrippedCharredDeadOakLog.get(),
            BlockSetup.StrippedCharredDeadOakWood.get()
        )

        tag(BlockTags.LOGS_THAT_BURN)
            .addTag(TagSetup.Blocks.DeadOakLogs)

        tag(BlockTags.PLANKS).add(BlockSetup.DeadOakPlanks.get())
        tag(BlockTags.WOODEN_SLABS).add(BlockSetup.DeadOakSlab.get())
        tag(BlockTags.WOODEN_STAIRS).add(BlockSetup.DeadOakStairs.get())
        tag(BlockTags.WOODEN_FENCES).add(BlockSetup.DeadOakFence.get())
        tag(BlockTags.WOODEN_BUTTONS).add(BlockSetup.DeadOakButton.get())
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(BlockSetup.DeadOakPressurePlate.get())
        tag(BlockTags.WOODEN_DOORS).add(BlockSetup.DeadOakDoor.get())
        tag(BlockTags.WOODEN_TRAPDOORS).add(BlockSetup.DeadOakTrapdoor.get())
    }

    private fun miningTags() {
        tag(BlockTags.NEEDS_STONE_TOOL)
            .add(BlockSetup.PorousStone.get())
        tag(BlockTags.MINEABLE_WITH_AXE)
            .addTag(TagSetup.Blocks.DeadOakLogs)
            .add(
                BlockSetup.DeadOakPlanks.get(),
                BlockSetup.DeadOakSlab.get(),
                BlockSetup.DeadOakStairs.get(),
                BlockSetup.DeadOakFence.get(),
                BlockSetup.DeadOakButton.get(),
                BlockSetup.DeadOakPressurePlate.get(),
                BlockSetup.DeadOakDoor.get(),
                BlockSetup.DeadOakTrapdoor.get()
            )

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
            BlockSetup.DriedDirt.get(),
            BlockSetup.DriedSandstone.get(),
            BlockSetup.PorousStone.get()
        )

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
            BlockSetup.Slate.get(),
            BlockSetup.DriedSand.get()
        )
    }
}