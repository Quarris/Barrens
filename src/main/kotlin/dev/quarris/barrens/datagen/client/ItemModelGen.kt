package dev.quarris.barrens.datagen.client

import dev.quarris.barrens.ModRef
import dev.quarris.barrens.setup.ItemSetup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class ItemModelGen(output: PackOutput, exFileHelper: ExistingFileHelper) :
    ItemModelProvider(output, ModRef.ID, exFileHelper) {

    override fun registerModels() {
        basicItem(ItemSetup.RawIronNugget.get())
        basicItem(ItemSetup.RawGoldNugget.get())
        basicItem(ItemSetup.RawCopperNugget.get())
    }

    private fun key(item: Item): ResourceLocation {
        return BuiltInRegistries.ITEM.getKey(item)
    }

    private fun name(item: Item): String {
        return key(item).path
    }


}