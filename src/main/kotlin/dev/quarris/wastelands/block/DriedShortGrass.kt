package dev.quarris.wastelands.block

import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.core.BlockPos
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.common.IForgeShearable

class DriedShortGrass(properties: Properties) : BushBlock(properties), IForgeShearable {

    override fun mayPlaceOn(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return state.`is`(BlockSetup.DriedDirt.get()) || state.`is`(BlockTags.DIRT)
    }

    override fun getMaxVerticalOffset(): Float {
        return 0.4f
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shape
    }

    companion object {
        private val Shape = box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0)
    }
}