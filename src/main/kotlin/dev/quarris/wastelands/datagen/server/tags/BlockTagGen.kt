package dev.quarris.wastelands.datagen.server.tags

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.BlockTagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.tags.BlockTags
import net.neoforged.neoforge.common.data.BlockTagsProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class BlockTagGen(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper?
) : BlockTagsProvider(output, lookupProvider, ModRef.ID, existingFileHelper) {

    override fun addTags(provider: HolderLookup.Provider) {
        miningTags()
        miscTags()
    }

    private fun miscTags() {
        tag(BlockTagSetup.DEAD_OAK_LOGS).add(
            BlockSetup.DEAD_OAK_WOOD.get(),
            BlockSetup.STRIPPED_DEAD_OAK_LOG.get(),
            BlockSetup.STRIPPED_DEAD_OAK_WOOD.get(),
            BlockSetup.CHARRED_DEAD_OAK_LOG.get(),
            BlockSetup.CHARRED_DEAD_OAK_WOOD.get(),
            BlockSetup.STRIPPED_CHARRED_DEAD_OAK_LOG.get(),
            BlockSetup.STRIPPED_CHARRED_DEAD_OAK_WOOD.get()
        )

        tag(BlockTags.LOGS_THAT_BURN).add(
            BlockSetup.DEAD_OAK_LOG.get(),
            BlockSetup.DEAD_OAK_WOOD.get(),
            BlockSetup.STRIPPED_DEAD_OAK_LOG.get(),
            BlockSetup.STRIPPED_DEAD_OAK_WOOD.get(),
            BlockSetup.CHARRED_DEAD_OAK_LOG.get(),
            BlockSetup.CHARRED_DEAD_OAK_WOOD.get(),
            BlockSetup.STRIPPED_CHARRED_DEAD_OAK_LOG.get(),
            BlockSetup.STRIPPED_CHARRED_DEAD_OAK_WOOD.get()
        )

        tag(BlockTags.PLANKS).add(BlockSetup.DEAD_OAK_PLANKS.get())
        tag(BlockTags.WOODEN_SLABS).add(BlockSetup.DEAD_OAK_SLAB.get())
        tag(BlockTags.WOODEN_STAIRS).add(BlockSetup.DEAD_OAK_STAIRS.get())
        tag(BlockTags.WOODEN_FENCES).add(BlockSetup.DEAD_OAK_FENCE.get())
        tag(BlockTags.WOODEN_BUTTONS).add(BlockSetup.DEAD_OAK_BUTTON.get())
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(BlockSetup.DEAD_OAK_PRESSURE_PLATE.get())
        tag(BlockTags.WOODEN_DOORS).add(BlockSetup.DEAD_OAK_DOOR.get())
        tag(BlockTags.WOODEN_TRAPDOORS).add(BlockSetup.DEAD_OAK_TRAPDOOR.get())
    }

    private fun miningTags() {
        tag(BlockTags.MINEABLE_WITH_AXE)
            .addTag(BlockTagSetup.DEAD_OAK_LOGS)
            .add(
                BlockSetup.DEAD_OAK_PLANKS.get(),
                BlockSetup.DEAD_OAK_SLAB.get(),
                BlockSetup.DEAD_OAK_STAIRS.get(),
                BlockSetup.DEAD_OAK_FENCE.get(),
                BlockSetup.DEAD_OAK_BUTTON.get(),
                BlockSetup.DEAD_OAK_PRESSURE_PLATE.get(),
                BlockSetup.DEAD_OAK_DOOR.get(),
                BlockSetup.DEAD_OAK_TRAPDOOR.get()
            )

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
            BlockSetup.DRIED_DIRT.get()
        )

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
            BlockSetup.SLATE.get()
        )
    }
}