package dev.quarris.barrens

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.config.ModConfigEvent


@EventBusSubscriber(modid = ModRef.ID, bus = EventBusSubscriber.Bus.MOD)
object BarrensConfigs {

    private val Builder = ForgeConfigSpec.Builder()

    private val UseBarrensDefaultPreset = Builder
        .comment("Should Barrens preset be used as the default World Type")
        .define("use_barrens_default_preset", false)

    private val RegionWeight = Builder
        .comment("The weight Terrablender uses for the Barrens region.")
        .comment("Defines how often the Barrens biomes should spawn with respect to vanilla or other mods.")
        .comment("Higher values makes the biomes spawn more often.")
        .define("region_weight", 2)

    val Spec: ForgeConfigSpec = Builder.build()

    var useBarrensDefaultPreset = false
    var regionWeight = 2

    @SubscribeEvent
    fun onLoad(event: ModConfigEvent) {
        useBarrensDefaultPreset = UseBarrensDefaultPreset.get()
        regionWeight = RegionWeight.get()
    }

}