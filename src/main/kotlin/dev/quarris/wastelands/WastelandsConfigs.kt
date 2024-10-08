package dev.quarris.wastelands

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.config.ModConfigEvent


@EventBusSubscriber(modid = ModRef.ID, bus = EventBusSubscriber.Bus.MOD)
object WastelandsConfigs {

    private val Builder = ForgeConfigSpec.Builder()

    private val UseWastelandsDefaultPreset = Builder
        .comment("Should Wastelands preset be used as the default World Type")
        .define("use_wastelands_default_preset", false)

    val Spec = Builder.build()

    var useWastelandsDefaultPreset = false

    @SubscribeEvent
    fun onLoad(event: ModConfigEvent) {
        useWastelandsDefaultPreset = UseWastelandsDefaultPreset.get()
    }

}