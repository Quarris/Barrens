package dev.quarris.wastelands.terrablender

import dev.quarris.wastelands.setup.BiomeSetup
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.SurfaceRules
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource


object SurfaceRuleSetup {

    private val StoneState = makeStateRule(Blocks.STONE)
    private val DriedDirtState = makeStateRule(BlockSetup.DriedDirt.get())

    fun makeRules(): RuleSource {
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeSetup.Wasteland), wastelandRules()),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeSetup.DeadOcean), wastelandRules()),
        )
    }

    private fun wastelandRules(): RuleSource {
        val surfaceDriedDirt = SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DriedDirtState)

        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(surfaceDriedDirt, StoneState)
            )
        )
    }

    private fun makeStateRule(block: Block): RuleSource {
        return SurfaceRules.state(block.defaultBlockState())
    }
}