package dev.quarris.wastelands.block

import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.neoforged.neoforge.common.ItemAbilities
import net.neoforged.neoforge.common.ItemAbility
import java.util.function.Supplier

class WoodBlock(
    properties: Properties,
    private val strippedBlock: Supplier<RotatedPillarBlock>
) : RotatedPillarBlock(properties) {

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(AXIS)
    }

    override fun getToolModifiedState(
        state: BlockState,
        context: UseOnContext,
        itemAbility: ItemAbility,
        simulate: Boolean
    ): BlockState? {
        if (itemAbility == ItemAbilities.AXE_STRIP) {
            return strippedBlock.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS))
        }

        return null
    }

}