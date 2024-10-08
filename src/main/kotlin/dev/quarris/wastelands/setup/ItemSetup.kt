package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

object ItemSetup {

    val Registry: DeferredRegister.Items = DeferredRegister.createItems(ModRef.ID)

    val RawIronNugget: DeferredItem<Item> = Registry.registerSimpleItem("raw_iron_nugget")
    val RawGoldNugget: DeferredItem<Item> = Registry.registerSimpleItem("raw_gold_nugget")
    val RawCopperNugget: DeferredItem<Item> = Registry.registerSimpleItem("raw_copper_nugget")

    fun init() {
        Registry.register(MOD_BUS)
    }

}