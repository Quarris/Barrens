package dev.quarris.barrens.worldgen.feature

import com.mojang.serialization.Codec
import dev.quarris.barrens.setup.BlockSetup
import net.minecraft.core.BlockPos
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.WorldGenLevel
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration

class DeadOakTreeFeature(codec: Codec<ProbabilityFeatureConfiguration>) : Feature<ProbabilityFeatureConfiguration>(codec) {

    private val deadOakWood = BlockSetup.DeadOakWood.get().defaultBlockState()
    private val charredDeadOakWood = BlockSetup.CharredDeadOakWood.get().defaultBlockState()

    override fun place(context: FeaturePlaceContext<ProbabilityFeatureConfiguration>): Boolean {
        val random = context.random()
        val level = context.level()
        val pos = context.origin()

        if (!level.getBlockState(pos.below()).`is`(BlockSetup.DriedDirt.get())) {
            return false
        }

        // Generate trunk
        val height = 1 + context.random().nextInt(2)
        for (i in 0..height) {
            if (level.getBlockState(pos.above(i)).`is`(BlockTags.REPLACEABLE_BY_TREES)) {
                level.setBlock(pos.above(i), deadLogOrCharred(random, 0.1f), 2)
            }
        }

        if (random.nextFloat() >= context.config().probability) {
            return true
        }

        // Generate tree
        val checked: MutableSet<BlockPos> = mutableSetOf()
        tryGenerateLog(level, pos.above(height + 1), random, 0.5f, checked)
        return true
    }

    private fun tryGenerateLog(
        level: WorldGenLevel,
        pos: BlockPos,
        random: RandomSource,
        chance: Float,
        checked: MutableSet<BlockPos>
    ) {
        if (random.nextFloat() > chance || checked.contains(pos)) {
            return
        }

        checked.add(pos)
        if (!level.isEmptyBlock(pos)) {
            return
        }

        level.setBlock(pos, deadLogOrCharred(random, chance), 2)
        for (xOff in -1..1) {
            for (zOff in -1..1) {
                for (yOff in 0..1) {
                    if (xOff == 0 && yOff == 0 && zOff == 0) {
                        continue
                    }

                    tryGenerateLog(
                        level,
                        pos.offset(xOff, yOff, zOff),
                        random,
                        chance - (0.15f + random.nextFloat() * 0.3f),
                        checked
                    )
                }
            }
        }
    }

    private fun deadLogOrCharred(random: RandomSource, charredChance: Float): BlockState {
        return if (random.nextFloat() < charredChance) charredDeadOakWood else deadOakWood
    }

}