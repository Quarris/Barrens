package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

object ItemSetup {

    val Registry: DeferredRegister.Items = DeferredRegister.createItems(ModRef.ID)

    fun init() {
        Registry.register(MOD_BUS)
    }

}