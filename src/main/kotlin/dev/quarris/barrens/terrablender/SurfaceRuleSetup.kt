package dev.quarris.barrens.terrablender

import dev.quarris.barrens.setup.BiomeSetup
import dev.quarris.barrens.setup.BlockSetup
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.SurfaceRules
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource


object SurfaceRuleSetup {

    private val Gravel: RuleSource = makeStateRule(Blocks.GRAVEL)
    private val StoneState = makeStateRule(Blocks.STONE)
    private val DriedDirtState = makeStateRule(BlockSetup.DriedDirt.get())
    private val DriedSandState = makeStateRule(BlockSetup.DriedSand.get())
    private val DriedSandstoneState = makeStateRule(BlockSetup.DriedSandstone.get())

    private val SurfaceDriedDirt = SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DriedDirtState)
    private val SurfaceDriedSand =
        SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, DriedDirtState), DriedSandState)

    fun makeRules(): RuleSource {
        return SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), barrens())
    }

    fun barrens(): RuleSource {
        val isAboveWater = SurfaceRules.waterBlockCheck(-1, 0)
        val waterCheck = SurfaceRules.waterStartCheck(-6, -1)
        val belowWater = SurfaceRules.waterBlockCheck(1, 0)
        val isOceanOrShore = SurfaceRules.isBiome(BiomeSetup.DeadOcean, BiomeSetup.WastelandShore)

        val dirt = DriedDirtState
        val ceilingSandstoneThenSand = SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, DriedSandstoneState),
            DriedSandState
        )
        val ceilingStoneThenGravel = SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, DriedDirtState),
            Gravel
        )
        val placeSand = SurfaceRules.ifTrue(isOceanOrShore, ceilingSandstoneThenSand)
        val placeSandThenDirt = SurfaceRules.sequence(
            placeSand,
            dirt
        )

        val finalSurface = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR,
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                        isAboveWater,
                        placeSandThenDirt
                    ),
                    SurfaceRules.ifTrue(belowWater, DriedSandState)
                )
            ),
            SurfaceRules.ifTrue(
                waterCheck,
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, placeSandThenDirt),
                    SurfaceRules.ifTrue(
                        isOceanOrShore,
                        SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, DriedSandstoneState)
                    )
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR,
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(BiomeSetup.DeadOcean),
                        ceilingSandstoneThenSand
                    ),
                    ceilingStoneThenGravel
                )
            )
        )

        return finalSurface
    }

    private fun makeStateRule(block: Block): RuleSource {
        return SurfaceRules.state(block.defaultBlockState())
    }
}