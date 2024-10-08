package dev.quarris.barrens.worldgen.feature

import com.mojang.serialization.Codec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration

class BoulderFeature(codec: Codec<BlockStateConfiguration>) : Feature<BlockStateConfiguration>(codec) {

    override fun place(context: FeaturePlaceContext<BlockStateConfiguration>): Boolean {
        var origin = context.origin()
        val level = context.level()
        val random = context.random()
        val config: BlockStateConfiguration = context.config()
        val groundState = level.getBlockState(origin.below())
        if (groundState.isAir || !groundState.fluidState.isEmpty) {
            return false
        }

        if (origin.y <= level.minBuildHeight + 3) {
            return false
        }

        for (i in 0..3) {
            val xRange = if (random.nextFloat() < 0.9) 1 else 0
            val yRange = if (random.nextFloat() < 0.9) 1 else 0
            val zRange = if (random.nextFloat() < 0.9) 1 else 0
            val range = (xRange + yRange + zRange).toFloat() / 3 + 0.5f
            for (pos in BlockPos.betweenClosed(origin.offset(-xRange, -yRange, -zRange), origin.offset(xRange, yRange, zRange))) {
                if (pos.distSqr(origin) <= (range * range).toDouble()) {
                    level.setBlock(pos, config.state, 3)
                }
            }
            origin = origin.offset(
                -1 + random.nextInt(3),
                -random.nextInt(2),
                -1 + random.nextInt(3)
            )
        }

        return true
    }
}