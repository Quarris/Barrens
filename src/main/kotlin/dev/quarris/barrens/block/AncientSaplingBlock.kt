package dev.quarris.barrens.block

import dev.quarris.barrens.setup.BlockSetup
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.SaplingBlock
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import net.minecraft.world.level.block.state.BlockState

class AncientSaplingBlock(pTreeGrower: AbstractTreeGrower, pProperties: Properties) :
    SaplingBlock(pTreeGrower, pProperties) {

    override fun mayPlaceOn(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos): Boolean {
        return super.mayPlaceOn(pState, pLevel, pPos) || pState.`is`(BlockSetup.DriedDirt.get())
    }

    override fun randomTick(pState: BlockState, pLevel: ServerLevel, pPos: BlockPos, pRandom: RandomSource) {
        // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (!pLevel.isAreaLoaded(pPos, 1)) return
        if (pLevel.getMaxLocalRawBrightness(pPos.above()) >= 3 && pRandom.nextInt(3) == 0) {
            this.advanceTree(pLevel, pPos, pState, pRandom)
        }
    }
}