package dev.quarris.wastelands.terrablender

import dev.quarris.wastelands.setup.BiomeSetup
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.SurfaceRules
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource
import net.minecraft.world.level.levelgen.placement.CaveSurface


object SurfaceRuleSetup {

    private val STONE = makeStateRule(Blocks.STONE)
    private val DRIED_DIRT = makeStateRule(BlockSetup.DRIED_DIRT.get())

    private val COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT)
    private val GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK)
    private val BLUE_TERRACOTTA = makeStateRule(Blocks.BLUE_TERRACOTTA)

    fun makeRules(): RuleSource {
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeSetup.WASTELANDS), wastelandRules()),
        )
    }

    private fun wastelandRules() : RuleSource {
        val isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0)
        val surface = SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, false, 4, CaveSurface.FLOOR), DRIED_DIRT)
        return SurfaceRules.sequence(
            surface,
            STONE
        )
    }

    private fun makeStateRule(block: Block): RuleSource {
        return SurfaceRules.state(block.defaultBlockState())
    }
}