package dev.quarris.wastelands.block

import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.FluidTags
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.common.IForgeShearable

class DeadSeagrassBlock(properties: Properties) : BushBlock(properties), BonemealableBlock, LiquidBlockContainer, IForgeShearable {

    override fun isValidBonemealTarget(level: LevelReader, pos: BlockPos, state: BlockState, thing: Boolean): Boolean {
        return true
    }

    override fun isBonemealSuccess(level: Level, random: RandomSource, pos: BlockPos, state: BlockState): Boolean {
        return true
    }

    override fun performBonemeal(level: ServerLevel, random: RandomSource, pos: BlockPos, state: BlockState) {
        val bottom = BlockSetup.TallDeadSeagrass.get().defaultBlockState()
        val top = bottom.setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER)
        val abovePos = pos.above()
        if (level.getBlockState(abovePos).`is`(Blocks.WATER)) {
            level.setBlock(pos, bottom, 2)
            level.setBlock(abovePos, top, 2)
        }
    }

    override fun mayPlaceOn(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return state.isFaceSturdy(level, pos, Direction.UP) && !state.`is`(Blocks.MAGMA_BLOCK)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val fluidstate = context.level.getFluidState(context.clickedPos)
        return if (fluidstate.`is`(FluidTags.WATER) && fluidstate.amount == 8) super.getStateForPlacement(context) else null
    }

    override fun updateShape(
        state: BlockState,
        facing: Direction,
        facingState: BlockState,
        level: LevelAccessor,
        currentPos: BlockPos,
        facingPos: BlockPos
    ): BlockState {
        val blockstate = super.updateShape(state, facing, facingState, level, currentPos, facingPos)
        if (!blockstate.isAir) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level))
        }

        return blockstate
    }

    override fun getMaxVerticalOffset(): Float {
        return 0.15f
    }

    override fun getMaxHorizontalOffset(): Float {
        return 0.5f
    }

    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return SHAPE
    }

    override fun getFluidState(state: BlockState): FluidState {
        return Fluids.WATER.getSource(false)
    }

    override fun canPlaceLiquid(
        level: BlockGetter,
        pos: BlockPos,
        state: BlockState,
        fluid: Fluid
    ): Boolean {
        return false
    }

    override fun placeLiquid(
        level: LevelAccessor,
        pos: BlockPos,
        state: BlockState,
        fluid: FluidState
    ): Boolean {
        return false
    }

    companion object {
        val SHAPE: VoxelShape = box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0)
    }
}