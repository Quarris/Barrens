package dev.quarris.barrens.setup

import dev.quarris.barrens.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object CreativeTabSetup {

    val Registry: DeferredRegister<CreativeModeTab> = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModRef.ID)

    init {
        Registry.register("main") {
            CreativeModeTab.builder()
                .title(Component.translatable(ModRef.key("tab", "main")))
                .icon { ItemStack(BlockSetup.DriedDirt.get()) }
                .displayItems { _, output ->
                    ItemSetup.Registry.entries.forEach { item ->
                        output.accept(item.get())
                    }
                }
                .build()
        }
    }

    fun init() {
        Registry.register(MOD_BUS)
    }

}