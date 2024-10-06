package dev.quarris.wastelands.datagen.server.tags

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.ItemTagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ItemTagGen(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    blockTags: CompletableFuture<TagLookup<Block>>,
    existingFileHelper: ExistingFileHelper
) : ItemTagsProvider(output, lookupProvider, blockTags, ModRef.ID, existingFileHelper) {

    override fun addTags(provider: HolderLookup.Provider) {
        tag(ItemTagSetup.DEAD_OAK_LOGS).add(
            BlockSetup.DEAD_OAK_LOG.asItem(),
            BlockSetup.DEAD_OAK_WOOD.asItem(),
            BlockSetup.STRIPPED_DEAD_OAK_LOG.asItem(),
            BlockSetup.STRIPPED_DEAD_OAK_WOOD.asItem(),
            BlockSetup.CHARRED_DEAD_OAK_LOG.asItem(),
            BlockSetup.CHARRED_DEAD_OAK_WOOD.asItem(),
            BlockSetup.STRIPPED_CHARRED_DEAD_OAK_LOG.asItem(),
            BlockSetup.STRIPPED_CHARRED_DEAD_OAK_WOOD.asItem(),
        )

        tag(ItemTags.PLANKS).add(BlockSetup.DEAD_OAK_PLANKS.asItem())
        tag(ItemTags.WOODEN_SLABS).add(BlockSetup.DEAD_OAK_SLAB.asItem())
        tag(ItemTags.WOODEN_STAIRS).add(BlockSetup.DEAD_OAK_STAIRS.asItem())
        tag(ItemTags.WOODEN_FENCES).add(BlockSetup.DEAD_OAK_FENCE.asItem())
        tag(ItemTags.WOODEN_BUTTONS).add(BlockSetup.DEAD_OAK_BUTTON.asItem())
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(BlockSetup.DEAD_OAK_PRESSURE_PLATE.asItem())
        tag(ItemTags.WOODEN_DOORS).add(BlockSetup.DEAD_OAK_DOOR.asItem())
        tag(ItemTags.WOODEN_TRAPDOORS).add(BlockSetup.DEAD_OAK_TRAPDOOR.asItem())
    }
}