package dev.quarris.barrens

import dev.quarris.barrens.terrablender.SurfaceRuleSetup
import dev.quarris.barrens.terrablender.BarrensRegion
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager

object EventHandler {

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    object Mod {
        @SubscribeEvent
        fun commonSetup(event: FMLCommonSetupEvent) {
            event.enqueueWork {
                Regions.register(BarrensRegion())
                SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, ModRef.ID, SurfaceRuleSetup.makeRules());
            }
        }
    }

}