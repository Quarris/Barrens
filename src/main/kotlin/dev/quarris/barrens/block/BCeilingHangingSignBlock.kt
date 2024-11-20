package dev.quarris.barrens.block

import dev.quarris.barrens.block.entity.BHangingSignBlockEntity
import dev.quarris.barrens.block.entity.BSignBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.CeilingHangingSignBlock
import net.minecraft.world.level.block.StandingSignBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.WoodType

class BCeilingHangingSignBlock(
    pProperties: Properties, pType: WoodType
) : CeilingHangingSignBlock(pProperties, pType) {

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return BHangingSignBlockEntity(pPos, pState)
    }
}