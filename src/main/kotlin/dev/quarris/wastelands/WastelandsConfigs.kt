package dev.quarris.wastelands

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.config.ModConfigEvent
import net.neoforged.neoforge.common.ModConfigSpec


@EventBusSubscriber(modid = ModRef.ID, bus = EventBusSubscriber.Bus.MOD)
object WastelandsConfigs {

    private val BUILDER = ModConfigSpec.Builder()

    private val USE_WASTELANDS_DEFAULT_PRESET = BUILDER
        .comment("Should Wastelands preset be used as the default World Type")
        .define("use_wastelands_default_preset", false)

    val SPEC = BUILDER.build()

    var useWastelandsDefaultPreset = false

    @SubscribeEvent
    fun onLoad(event: ModConfigEvent) {
        useWastelandsDefaultPreset = USE_WASTELANDS_DEFAULT_PRESET.get()
    }

}