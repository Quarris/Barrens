package dev.quarris.wastelands.block

import dev.quarris.wastelands.setup.PlacedFeatureSetup
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import java.util.*

class DriedDirtBlock(properties: Properties) : Block(properties), BonemealableBlock {
    override fun isValidBonemealTarget(level: LevelReader, pos: BlockPos, state: BlockState): Boolean {
        return level.getBlockState(pos.above()).isAir
    }

    override fun isBonemealSuccess(level: Level, random: RandomSource, pos: BlockPos, state: BlockState): Boolean {
        return true
    }

    override fun performBonemeal(level: ServerLevel, random: RandomSource, pos: BlockPos, state: BlockState) {
        val origin: BlockPos = pos.above()
        val boneMealVegetationFeature: Optional<Holder.Reference<PlacedFeature>> = level.registryAccess()
            .registryOrThrow(Registries.PLACED_FEATURE)
            .getHolder(PlacedFeatureSetup.DriedGrassBonemeal)

        if (boneMealVegetationFeature.isEmpty) return

        // Do some 128 passes
        label49@ for (i in 0..127) {
            var placementPos = origin

            // Compute a random position
            for (j in 0 until i / 16) {
                placementPos = placementPos.offset(
                    random.nextInt(3) - 1,
                    (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                    random.nextInt(3) - 1
                )
                if (!level.getBlockState(placementPos.below()).`is`(this) || level.getBlockState(placementPos)
                        .isCollisionShapeFullBlock(level, placementPos)
                ) {
                    continue@label49
                }
            }

            val placementState: BlockState = level.getBlockState(placementPos)

            // If the placement position has air, place the feature
            if (placementState.isAir) {
                boneMealVegetationFeature.get().value().place(level, level.chunkSource.generator, random, placementPos)
            }
        }
    }


}