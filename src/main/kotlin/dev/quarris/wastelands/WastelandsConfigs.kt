package dev.quarris.wastelands

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.config.ModConfigEvent
import net.neoforged.neoforge.common.ModConfigSpec


@EventBusSubscriber(modid = ModRef.ID, bus = EventBusSubscriber.Bus.MOD)
object WastelandsConfigs {

    private val Builder = ModConfigSpec.Builder()

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