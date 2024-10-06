package dev.quarris.wastelands.block

import com.mojang.serialization.MapCodec
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.core.BlockPos
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.FarmBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.neoforged.neoforge.common.IShearable

class DriedShortGrass(properties: Properties) : BushBlock(properties), IShearable {

    override fun mayPlaceOn(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return state.`is`(BlockSetup.DRIED_DIRT) || state.`is`(BlockTags.DIRT)
    }

    override fun getMaxVerticalOffset(): Float {
        return 0.4f
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

    override fun codec(): MapCodec<out BushBlock> {
        return CODEC
    }

    companion object {
        private val CODEC = simpleCodec(::DriedShortGrass)
        private val SHAPE = box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)
    }
}