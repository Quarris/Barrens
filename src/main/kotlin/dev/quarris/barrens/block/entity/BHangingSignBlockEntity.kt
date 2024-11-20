package dev.quarris.barrens.block.entity

import dev.quarris.barrens.setup.BlockEntitySetup
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.HangingSignBlockEntity
import net.minecraft.world.level.block.state.BlockState

class BHangingSignBlockEntity(
    pPos: BlockPos, pBlockState: BlockState
) : HangingSignBlockEntity(BlockEntitySetup.HangingSign.get(), pPos, pBlockState) {

}