package dev.quarris.barrens.mixins;

import dev.quarris.barrens.block.DeadSeagrassBlock;
import dev.quarris.barrens.setup.BlockSetup;
import dev.quarris.barrens.setup.TagSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

    @Inject(method = "growWaterPlant", at = @At("HEAD"), cancellable = true)
    private static void growDeadWaterPlants(ItemStack stack, Level level, BlockPos pos, Direction clickedSide, CallbackInfoReturnable<Boolean> cir) {
        if (!level.getBiome(pos).is(TagSetup.Biomes.INSTANCE.getIsDead()) || !level.getBlockState(pos).is(Blocks.WATER) || level.getFluidState(pos).getAmount() != 8) {
            return;
        }

        if (!(level instanceof ServerLevel)) {
            cir.setReturnValue(true);
            return;
        }

        RandomSource randomsource = level.getRandom();

        label78:
        for (int i = 0; i < 128; i++) {
            BlockPos placementPos = pos;
            DeadSeagrassBlock seagrassBlock = BlockSetup.INSTANCE.getDeadSeagrass().get();
            BlockState toPlace = seagrassBlock.defaultBlockState();

            for (int j = 0; j < i / 16; j++) {
                placementPos = placementPos.offset(
                    randomsource.nextInt(3) - 1, (randomsource.nextInt(3) - 1) * randomsource.nextInt(3) / 2, randomsource.nextInt(3) - 1
                );
                if (level.getBlockState(placementPos).isCollisionShapeFullBlock(level, placementPos)) {
                    continue label78;
                }
            }

            if (toPlace.canSurvive(level, placementPos)) {
                BlockState currentState = level.getBlockState(placementPos);
                if (currentState.is(Blocks.WATER) && level.getFluidState(placementPos).getAmount() == 8) {
                    level.setBlock(placementPos, toPlace, 3);
                } else if (currentState.is(seagrassBlock) && randomsource.nextInt(10) == 0) {
                    seagrassBlock.performBonemeal((ServerLevel) level, randomsource, placementPos, currentState);
                }
            }

        }

        stack.shrink(1);
        cir.setReturnValue(true);
    }

}
