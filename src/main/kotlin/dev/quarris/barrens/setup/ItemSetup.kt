package dev.quarris.barrens.setup

import dev.quarris.barrens.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.HangingSignItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.SignItem
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import java.util.function.Supplier

object ItemSetup {

    val Registry: DeferredRegister<Item> = DeferredRegister.create(Registries.ITEM, ModRef.ID)

    val RawIronNugget: RegistryObject<Item> = registerSimpleItem("raw_iron_nugget")
    val RawGoldNugget: RegistryObject<Item> = registerSimpleItem("raw_gold_nugget")
    val RawCopperNugget: RegistryObject<Item> = registerSimpleItem("raw_copper_nugget")

    val DeadOakSign: RegistryObject<SignItem> = register("dead_oak_sign") {
        SignItem(
            Item.Properties(),
            BlockSetup.DeadOakSign.get(),
            BlockSetup.DeadOakWallSign.get()
        )
    }

    val DeadOakHangingSign: RegistryObject<HangingSignItem> = register("dead_oak_hanging_sign") {
        HangingSignItem(
            BlockSetup.DeadOakHangingSign.get(),
            BlockSetup.DeadOakWallHangingSign.get(),
            Item.Properties()
        )
    }

    fun registerSimpleItem(name: String): RegistryObject<Item> {
        return Registry.register(name) { Item(Item.Properties()) }
    }

    fun <B : Block> registerBlockItem(block: RegistryObject<B>): RegistryObject<BlockItem> {
        return Registry.register(block.id.path) { BlockItem(block.get(), Item.Properties()) }
    }

    fun <I : Item> register(name: String, item: Supplier<I>): RegistryObject<I> {
        return Registry.register(name, item)
    }

    fun init() {
        Registry.register(MOD_BUS)
    }

}