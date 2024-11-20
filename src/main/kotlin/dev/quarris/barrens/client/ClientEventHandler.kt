package dev.quarris.barrens.client

import dev.quarris.barrens.setup.BlockEntitySetup
import dev.quarris.barrens.setup.WoodTypeSetup
import net.minecraft.client.renderer.Sheets
import net.minecraft.client.renderer.blockentity.HangingSignRenderer
import net.minecraft.client.renderer.blockentity.SignRenderer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@EventBusSubscriber(value = [Dist.CLIENT])
object ClientEventHandler {

    @SubscribeEvent
    fun clientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
            Sheets.addWoodType(WoodTypeSetup.DeadOak)
        }
    }

    @EventBusSubscriber(value = [Dist.CLIENT], bus = EventBusSubscriber.Bus.MOD)
    object Mod {

        @SubscribeEvent
        fun clientSetup(event: EntityRenderersEvent.RegisterRenderers) {
            event.registerBlockEntityRenderer(BlockEntitySetup.Sign.get(), ::SignRenderer)
            event.registerBlockEntityRenderer(BlockEntitySetup.HangingSign.get(), ::HangingSignRenderer)
        }

    }

}