package dev.quarris.wastelands.terrablender

import dev.quarris.wastelands.setup.BiomeSetup
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.Noises
import net.minecraft.world.level.levelgen.SurfaceRules
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource


object SurfaceRuleSetup {

    private val StoneState = makeStateRule(Blocks.STONE)
    private val DriedDirtState = makeStateRule(BlockSetup.DriedDirt.get())
    private val DriedSandState = makeStateRule(BlockSetup.DriedSand.get())
    private val DriedSandstoneState = makeStateRule(BlockSetup.DriedSandstone.get())
    private val SurfaceDriedDirt = SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DriedDirtState)
    private val SurfaceDriedSand =
        SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, DriedDirtState), DriedSandState)

    fun wasteland(): RuleSource {
        return SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), barrenLands())

        /*SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeSetup.Wasteland), wastelandRules()),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeSetup.WastelandCoast), wastelandCoastRules()),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeSetup.DeadOcean), deadOceanRules()),
        )*/
    }

    private fun wastelandRules(): RuleSource {
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(SurfaceDriedDirt, StoneState)
            )
        )
    }

    private fun wastelandCoastRules(): RuleSource {
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                        SurfaceRules.waterStartCheck(-1, 0),
                        SurfaceDriedSand
                    ),
                    SurfaceDriedSand,
                    StoneState
                )
            )
        )
    }

    private fun deadOceanRules(): RuleSource {
        val surfaceDriedSand = SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DriedDirtState)

        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(surfaceDriedSand, StoneState)
            )
        )
    }

    fun barrenLands(): RuleSource {
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
            GRAVEL
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


    val AIR: RuleSource = makeStateRule(Blocks.AIR)
    val BEDROCK: RuleSource = makeStateRule(Blocks.BEDROCK)
    val WHITE_TERRACOTTA: RuleSource = makeStateRule(Blocks.WHITE_TERRACOTTA)
    val ORANGE_TERRACOTTA: RuleSource = makeStateRule(Blocks.ORANGE_TERRACOTTA)
    val TERRACOTTA: RuleSource = makeStateRule(Blocks.TERRACOTTA)
    val RED_SAND: RuleSource = makeStateRule(Blocks.RED_SAND)
    val RED_SANDSTONE: RuleSource = makeStateRule(Blocks.RED_SANDSTONE)
    val STONE: RuleSource = makeStateRule(Blocks.STONE)
    val DEEPSLATE: RuleSource = makeStateRule(Blocks.DEEPSLATE)
    val DIRT: RuleSource = makeStateRule(Blocks.DIRT)
    val PODZOL: RuleSource = makeStateRule(Blocks.PODZOL)
    val COARSE_DIRT: RuleSource = makeStateRule(Blocks.COARSE_DIRT)
    val MYCELIUM: RuleSource = makeStateRule(Blocks.MYCELIUM)
    val GRASS_BLOCK: RuleSource = makeStateRule(Blocks.GRASS_BLOCK)
    val CALCITE: RuleSource = makeStateRule(Blocks.CALCITE)
    val GRAVEL: RuleSource = makeStateRule(Blocks.GRAVEL)
    val SAND: RuleSource = makeStateRule(Blocks.SAND)
    val SANDSTONE: RuleSource = makeStateRule(Blocks.SANDSTONE)
    val PACKED_ICE: RuleSource = makeStateRule(Blocks.PACKED_ICE)
    val SNOW_BLOCK: RuleSource = makeStateRule(Blocks.SNOW_BLOCK)
    val MUD: RuleSource = makeStateRule(Blocks.MUD)
    val POWDER_SNOW: RuleSource = makeStateRule(Blocks.POWDER_SNOW)
    val ICE: RuleSource = makeStateRule(Blocks.ICE)
    val WATER: RuleSource = makeStateRule(Blocks.WATER)
    val LAVA: RuleSource = makeStateRule(Blocks.LAVA)
    val NETHERRACK: RuleSource = makeStateRule(Blocks.NETHERRACK)
    val SOUL_SAND: RuleSource = makeStateRule(Blocks.SOUL_SAND)
    val SOUL_SOIL: RuleSource = makeStateRule(Blocks.SOUL_SOIL)
    val BASALT: RuleSource = makeStateRule(Blocks.BASALT)
    val BLACKSTONE: RuleSource = makeStateRule(Blocks.BLACKSTONE)
    val WARPED_WART_BLOCK: RuleSource = makeStateRule(Blocks.WARPED_WART_BLOCK)
    val WARPED_NYLIUM: RuleSource = makeStateRule(Blocks.WARPED_NYLIUM)
    val NETHER_WART_BLOCK: RuleSource = makeStateRule(Blocks.NETHER_WART_BLOCK)
    val CRIMSON_NYLIUM: RuleSource = makeStateRule(Blocks.CRIMSON_NYLIUM)
    val ENDSTONE: RuleSource = makeStateRule(Blocks.END_STONE)

    private fun surfaceNoiseAbove(value: Double): ConditionSource {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25, Double.MAX_VALUE)
    }

    private fun makeStateRule(block: Block): RuleSource {
        return SurfaceRules.state(block.defaultBlockState())
    }
}