package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object ItemSetup {

    val Registry: DeferredRegister<Item> = DeferredRegister.create(Registries.ITEM, ModRef.ID)

    val RawIronNugget: RegistryObject<Item> = registerSimpleItem("raw_iron_nugget")
    val RawGoldNugget: RegistryObject<Item> = registerSimpleItem("raw_gold_nugget")
    val RawCopperNugget: RegistryObject<Item> = registerSimpleItem("raw_copper_nugget")

    fun registerSimpleItem(name: String): RegistryObject<Item> {
        return Registry.register(name) { Item(Item.Properties()) }
    }

    fun <B : Block> registerBlockItem(block: RegistryObject<B>): RegistryObject<BlockItem> {
        return Registry.register(block.id.path) { BlockItem(block.get(), Item.Properties()) }
    }

    fun init() {
        Registry.register(MOD_BUS)
    }

}