package dev.quarris.barrens

import dev.quarris.barrens.terrablender.BarrensRegion
import dev.quarris.barrens.terrablender.SurfaceRuleSetup
import net.minecraft.server.dedicated.Settings
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.util.SortedProperties
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

object EventHandler {

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    object Mod {
        @SubscribeEvent
        fun commonSetup(event: FMLCommonSetupEvent) {
            event.enqueueWork {
                Regions.register(BarrensRegion())
                SurfaceRuleManager.addSurfaceRules(
                    SurfaceRuleManager.RuleCategory.OVERWORLD,
                    ModRef.ID,
                    SurfaceRuleSetup.makeRules()
                );
            }
        }
    }

    @EventBusSubscriber(value = [Dist.DEDICATED_SERVER], bus = EventBusSubscriber.Bus.MOD)
    object ServerMod {
        @SubscribeEvent
        fun serverSetup(event: FMLDedicatedServerSetupEvent) {
            event.enqueueWork {
                if (BarrensConfigs.useBarrensDefaultPreset) {
                    val path = Paths.get("server.properties")
                    val props = Settings.loadFromFile(path)
                    props.setProperty("level-type", "barrens:barrens")
                    Files.newBufferedWriter(path, StandardCharsets.UTF_8).use { writer -> SortedProperties.store(props, writer, "Minecraft server properties") }
                }
            }
        }
    }

}