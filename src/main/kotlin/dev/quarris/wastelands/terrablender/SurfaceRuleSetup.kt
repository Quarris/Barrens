package dev.quarris.wastelands.terrablender

import dev.quarris.wastelands.setup.BiomeSetup
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.SurfaceRules
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource


object SurfaceRuleSetup {

    private val STONE = makeStateRule(Blocks.STONE)
    private val DRIED_DIRT = makeStateRule(BlockSetup.DRIED_DIRT.get())

    fun makeRules(): RuleSource {
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeSetup.WASTELAND), wastelandRules()),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeSetup.DEAD_OCEAN), wastelandRules()),
        )
    }

    private fun wastelandRules(): RuleSource {
        val isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0)
        val surfaceDriedDirt = SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DRIED_DIRT)
        val stoneUnderDriedDirt = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), STONE)

        val grass = SurfaceRules.ifTrue(
            SurfaceRules.abovePreliminarySurface(), SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(
                    SurfaceRules.waterBlockCheck(-1, 0), SurfaceRules.sequence(
                        DRIED_DIRT
                    )
                )
            )
        )

        val dirt = SurfaceRules.ifTrue(
            SurfaceRules.waterStartCheck(-6, -1),
            SurfaceRules.ifTrue(
                SurfaceRules.UNDER_FLOOR, DRIED_DIRT
            )
        )

        val underDirt = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR,
                SurfaceRules.ifTrue(
                    SurfaceRules.waterBlockCheck(-1, 0),
                    SurfaceRules.sequence(
                        grass
                    )
                )
            ),
            dirt
        )

        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(surfaceDriedDirt, STONE)
            )
        )
    }

    private fun makeStateRule(block: Block): RuleSource {
        return SurfaceRules.state(block.defaultBlockState())
    }
}