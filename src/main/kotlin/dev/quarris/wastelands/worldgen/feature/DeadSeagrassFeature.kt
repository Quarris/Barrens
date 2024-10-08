package dev.quarris.wastelands.worldgen.feature

import com.mojang.serialization.Codec
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.TallSeagrassBlock
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration

class DeadSeagrassFeature(codec: Codec<ProbabilityFeatureConfiguration>) :
    Feature<ProbabilityFeatureConfiguration>(codec) {

    override fun place(context: FeaturePlaceContext<ProbabilityFeatureConfiguration>): Boolean {
        val random = context.random()
        val level = context.level()
        val origin = context.origin()
        val config = context.config()
        val offsetX = random.nextInt(8) - random.nextInt(8)
        val offsetZ = random.nextInt(8) - random.nextInt(8)
        val height = level.getHeight(Heightmap.Types.OCEAN_FLOOR, origin.x + offsetX, origin.z + offsetZ)
        val placementPos = BlockPos(origin.x + offsetX, height, origin.z + offsetZ)
        if (level.getBlockState(placementPos).`is`(Blocks.WATER)) {
            val spawnTall = random.nextDouble() < config.probability.toDouble()
            val toSpawn =
                if (spawnTall)
                    BlockSetup.TallDeadSeagrass.get().defaultBlockState()
                else
                    BlockSetup.DeadSeagrass.get().defaultBlockState()

            if (toSpawn.canSurvive(level, placementPos)) {
                if (spawnTall) {
                    val upperState = toSpawn.setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER)
                    val upperPos = placementPos.above()
                    if (level.getBlockState(upperPos).`is`(Blocks.WATER)) {
                        level.setBlock(placementPos, toSpawn, 2)
                        level.setBlock(upperPos, upperState, 2)
                    }
                } else {
                    level.setBlock(placementPos, toSpawn, 2)
                }

                return true
            }
        }

        return false
    }
}