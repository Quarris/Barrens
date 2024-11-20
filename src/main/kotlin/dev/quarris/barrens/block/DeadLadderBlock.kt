package dev.quarris.barrens.block

import dev.quarris.barrens.ModRef
import net.minecraft.world.level.block.LadderBlock
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

class DeadLadderBlock(pProperties: Properties) : LadderBlock(pProperties) {

    @EventBusSubscriber(modid = ModRef.ID)
    object Events {
        @SubscribeEvent
        fun oof(event: LivingTickEvent) {
            val entity = event.entity
            if (!entity.level().isClientSide() && entity.onClimbable()) {
                if (entity.lastClimbablePos.isEmpty) return

                val ladderPos = entity.lastClimbablePos.get()
                val block = entity.level().getBlockState(ladderPos).block
                if (block is DeadLadderBlock && entity.random.nextFloat() < 0.005f) {
                    entity.level().destroyBlock(ladderPos, false, entity)
                }
            }
        }
    }
}