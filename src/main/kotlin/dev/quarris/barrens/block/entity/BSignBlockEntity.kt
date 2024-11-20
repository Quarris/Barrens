package dev.quarris.barrens.block.entity

import dev.quarris.barrens.setup.BlockEntitySetup
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.SignBlockEntity
import net.minecraft.world.level.block.state.BlockState

class BSignBlockEntity(
    pPos: BlockPos, pBlockState: BlockState
) : SignBlockEntity(BlockEntitySetup.Sign.get(), pPos, pBlockState) {

}