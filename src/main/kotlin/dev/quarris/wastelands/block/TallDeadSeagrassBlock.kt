package dev.quarris.wastelands.block

import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.FluidTags
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DoublePlantBlock
import net.minecraft.world.level.block.LiquidBlockContainer
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class TallDeadSeagrassBlock(properties: Properties) : DoublePlantBlock(properties), LiquidBlockContainer {

    override fun mayPlaceOn(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return state.isFaceSturdy(level, pos, Direction.UP) && !state.`is`(Blocks.MAGMA_BLOCK)
    }

    override fun getCloneItemStack(state: BlockState, hit: HitResult, level: BlockGetter, pos: BlockPos, player: Player): ItemStack {
        return ItemStack(BlockSetup.DeadSeagrass.get())
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val state = super.getStateForPlacement(context)
        if (state != null) {
            val fluid = context.level.getFluidState(context.clickedPos.above())
            if (fluid.`is`(FluidTags.WATER) && fluid.amount == 8) {
                return state
            }
        }

        return null
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        if (state.getValue(Half) == DoubleBlockHalf.UPPER) {
            val blockstate = level.getBlockState(pos.below())
            return blockstate.`is`(this) && blockstate.getValue(Half) == DoubleBlockHalf.LOWER
        } else {
            val fluidstate = level.getFluidState(pos)
            return super.canSurvive(state, level, pos) && fluidstate.`is`(FluidTags.WATER) && fluidstate.amount == 8
        }
    }

    override fun getFluidState(state: BlockState): FluidState {
        return Fluids.WATER.getSource(false)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shape
    }

    override fun canPlaceLiquid(level: BlockGetter, pos: BlockPos, state: BlockState, fluid: Fluid): Boolean {
        return false
    }

    override fun placeLiquid(level: LevelAccessor, pos: BlockPos, state: BlockState, fluid: FluidState): Boolean {
        return false
    }

    companion object {
        val Half: EnumProperty<DoubleBlockHalf> = HALF
        val Shape: VoxelShape = box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)
    }

}