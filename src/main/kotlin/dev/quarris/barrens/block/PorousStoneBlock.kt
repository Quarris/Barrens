package dev.quarris.barrens.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.ticks.TickPriority

class PorousStoneBlock(properties: Properties) : Block(properties) {

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        level.destroyBlock(pos, true)
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        previousNeighbour: Block,
        neighborPos: BlockPos,
        movedByPiston: Boolean
    ) {
        val currentNeighbourState = level.getBlockState(neighborPos)
        if (!currentNeighbourState.isAir || previousNeighbour != this) {
            return
        }

        if (level.random.nextFloat() < 0.3f) {
            val delay = 2 + level.random.nextInt(8)
            level.scheduleTick(pos, this, delay, TickPriority.VERY_LOW)
        }
    }
}