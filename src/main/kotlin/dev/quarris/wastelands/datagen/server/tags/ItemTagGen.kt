package dev.quarris.wastelands.datagen.server.tags

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.TagSetup
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
        tag(ItemTags.DIRT).add(BlockSetup.DriedDirt.asItem())
        tag(Tags.Items.SANDSTONE_BLOCKS).add(BlockSetup.DriedSandstone.asItem())

        BlockSetup.DriedSand.asItem().let { sand ->
            tag(ItemTags.SAND).add(sand)
            tag(ItemTags.SMELTS_TO_GLASS).add(sand)
        }

        tag(TagSetup.Items.DeadOakLogs).add(
            BlockSetup.DeadOakLog.asItem(),
            BlockSetup.DeadOakWood.asItem(),
            BlockSetup.StrippedDeakOakLog.asItem(),
            BlockSetup.StrippedDeadOakWood.asItem(),
            BlockSetup.CharredDeadOakLog.asItem(),
            BlockSetup.CharredDeadOakWood.asItem(),
            BlockSetup.StrippedCharredDeadOakLog.asItem(),
            BlockSetup.StrippedCharredDeadOakWood.asItem(),
        )

        tag(ItemTags.PLANKS).add(BlockSetup.DeadOakPlanks.asItem())
        tag(ItemTags.WOODEN_SLABS).add(BlockSetup.DeadOakSlab.asItem())
        tag(ItemTags.WOODEN_STAIRS).add(BlockSetup.DeadOakStairs.asItem())
        tag(ItemTags.WOODEN_FENCES).add(BlockSetup.DeadOakFence.asItem())
        tag(ItemTags.WOODEN_BUTTONS).add(BlockSetup.DeadOakButton.asItem())
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(BlockSetup.DeadOakPressurePlate.asItem())
        tag(ItemTags.WOODEN_DOORS).add(BlockSetup.DeadOakDoor.asItem())
        tag(ItemTags.WOODEN_TRAPDOORS).add(BlockSetup.DeadOakTrapdoor.asItem())
    }
}