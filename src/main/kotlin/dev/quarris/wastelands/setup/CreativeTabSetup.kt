package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import java.util.function.Supplier

object CreativeTabSetup {

    val Registry: DeferredRegister<CreativeModeTab> = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModRef.ID)

    init {
        Registry.register("main", Supplier {
            CreativeModeTab.builder()
                .title(Component.translatable(ModRef.key("tab", "main")))
                .icon { ItemStack(BlockSetup.DriedDirt) }
                .displayItems { _, output ->
                    ItemSetup.Registry.entries.forEach { item ->
                        output.accept(item.get())
                    }
                }
                .build()
        })
    }

    fun init() {
        Registry.register(MOD_BUS)
    }

}