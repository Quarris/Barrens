package dev.quarris.barrens.datagen.server.tags

import dev.quarris.barrens.ModRef
import dev.quarris.barrens.setup.BlockSetup
import dev.quarris.barrens.setup.ItemSetup
import dev.quarris.barrens.setup.TagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ItemTagGen(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    blockTags: CompletableFuture<TagLookup<Block>>,
    existingFileHelper: ExistingFileHelper
) : ItemTagsProvider(output, lookupProvider, blockTags, ModRef.ID, existingFileHelper) {

    override fun addTags(provider: HolderLookup.Provider) {
        rawOres()

        tag(ItemTags.DIRT).add(BlockSetup.DriedDirt.get().asItem())
        tag(Tags.Items.SANDSTONE).add(BlockSetup.DriedSandstone.get().asItem())
        tag(ItemTags.SAPLINGS).add(BlockSetup.AncientOakSapling.get().asItem())

        BlockSetup.DriedSand.get().asItem().let { sand ->
            tag(ItemTags.SAND).add(sand)
            tag(ItemTags.SMELTS_TO_GLASS).add(sand)
        }

        tag(TagSetup.Items.DeadOakLogs).add(
            BlockSetup.DeadOakLog.get().asItem(),
            BlockSetup.DeadOakWood.get().asItem(),
            BlockSetup.StrippedDeakOakLog.get().asItem(),
            BlockSetup.StrippedDeadOakWood.get().asItem(),
            BlockSetup.CharredDeadOakLog.get().asItem(),
            BlockSetup.CharredDeadOakWood.get().asItem(),
            BlockSetup.StrippedCharredDeadOakLog.get().asItem(),
            BlockSetup.StrippedCharredDeadOakWood.get().asItem(),
        )

        tag(ItemTags.PLANKS).add(BlockSetup.DeadOakPlanks.get().asItem())
        tag(ItemTags.WOODEN_SLABS).add(BlockSetup.DeadOakSlab.get().asItem())
        tag(ItemTags.WOODEN_STAIRS).add(BlockSetup.DeadOakStairs.get().asItem())
        tag(ItemTags.WOODEN_FENCES).add(BlockSetup.DeadOakFence.get().asItem())
        tag(ItemTags.WOODEN_BUTTONS).add(BlockSetup.DeadOakButton.get().asItem())
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(BlockSetup.DeadOakPressurePlate.get().asItem())
        tag(ItemTags.WOODEN_DOORS).add(BlockSetup.DeadOakDoor.get().asItem())
        tag(ItemTags.WOODEN_TRAPDOORS).add(BlockSetup.DeadOakTrapdoor.get().asItem())
    }

    private fun rawOres() {
        tag(TagSetup.Items.RawMaterialsNuggetsIron).add(ItemSetup.RawIronNugget.get())
        tag(TagSetup.Items.RawMaterialsNuggetsGold).add(ItemSetup.RawGoldNugget.get())
        tag(TagSetup.Items.RawMaterialsNuggetsCopper).add(ItemSetup.RawCopperNugget.get())

        tag(TagSetup.Items.RawMaterialsNuggets).addTags(
            TagSetup.Items.RawMaterialsNuggetsIron,
            TagSetup.Items.RawMaterialsNuggetsGold,
            TagSetup.Items.RawMaterialsNuggetsCopper
        )
    }
}