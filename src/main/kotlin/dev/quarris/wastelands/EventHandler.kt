package dev.quarris.wastelands

import dev.quarris.wastelands.terrablender.SurfaceRuleSetup
import dev.quarris.wastelands.terrablender.WastelandsRegion
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager

object EventHandler {

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    object Mod {
        @SubscribeEvent
        private fun commonSetup(event: FMLCommonSetupEvent) {
            event.enqueueWork {
                Regions.register(WastelandsRegion())
                SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, ModRef.ID, SurfaceRuleSetup.wasteland());
            }
        }
    }

}