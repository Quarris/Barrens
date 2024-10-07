package dev.quarris.wastelands.setup

import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.levelgen.*
import net.minecraft.world.level.levelgen.DensityFunctions.HolderHolder
import net.minecraft.world.level.levelgen.OreVeinifier.VeinType
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters

object NoiseRouterSetup {

    val ZERO = createKey("zero")
    val Y = createKey("y")
    val SHIFT_X = createKey("shift_x")
    val SHIFT_Z = createKey("shift_z")
    val BASE_3D_NOISE_OVERWORLD = createKey("overworld/base_3d_noise")
    val BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise")
    val BASE_3D_NOISE_END = createKey("end/base_3d_noise")
    val SLOPED_CHEESE = createKey("overworld/sloped_cheese")
    val OFFSET_LARGE = createKey("overworld_large_biomes/offset")
    val FACTOR_LARGE = createKey("overworld_large_biomes/factor")
    val JAGGEDNESS_LARGE = createKey("overworld_large_biomes/jaggedness")
    val DEPTH_LARGE = createKey("overworld_large_biomes/depth")
    val SLOPED_CHEESE_LARGE = createKey("overworld_large_biomes/sloped_cheese")
    val OFFSET_AMPLIFIED = createKey("overworld_amplified/offset")
    val FACTOR_AMPLIFIED = createKey("overworld_amplified/factor")
    val JAGGEDNESS_AMPLIFIED = createKey("overworld_amplified/jaggedness")
    val DEPTH_AMPLIFIED = createKey("overworld_amplified/depth")
    val SLOPED_CHEESE_AMPLIFIED = createKey("overworld_amplified/sloped_cheese")
    val SLOPED_CHEESE_END = createKey("end/sloped_cheese")
    val SPAGHETTI_ROUGHNESS_FUNCTION = createKey("overworld/caves/spaghetti_roughness_function")
    val ENTRANCES = createKey("overworld/caves/entrances")
    val NOODLE = createKey("overworld/caves/noodle")
    val PILLARS = createKey("overworld/caves/pillars")
    val SPAGHETTI_2D_THICKNESS_MODULATOR = createKey("overworld/caves/spaghetti_2d_thickness_modulator")
    val SPAGHETTI_2D = createKey("overworld/caves/spaghetti_2d")

    fun wastelands(
        densityFunctions: HolderGetter<DensityFunction>, noiseParameters: HolderGetter<NoiseParameters>
    ): NoiseRouter {
        val aquiferBarrier = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5)
        val fluidFloodedness = DensityFunctions.noise(
            noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67
        )
        val fluidSpread = DensityFunctions.noise(
            noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143
        )
        val aquiferLava = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA))
        val shiftX = getFunction(densityFunctions, SHIFT_X)
        val shiftZ = getFunction(densityFunctions, SHIFT_Z)
        val temperatureNoise = DensityFunctions.shiftedNoise2d(
            shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE)
        )
        val vegetationNoise = DensityFunctions.shiftedNoise2d(
            shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.VEGETATION)
        )
        val factor = getFunction(densityFunctions, NoiseRouterData.FACTOR)
        val depth = getFunction(densityFunctions, NoiseRouterData.DEPTH)
        val noiseGradient = noiseGradientDensity(DensityFunctions.cache2d(factor), depth)
        val slopedCheese = getFunction(
            densityFunctions, SLOPED_CHEESE
        )
        val entrances = DensityFunctions.min(
            slopedCheese, DensityFunctions.mul(
                DensityFunctions.constant(5.0), getFunction(densityFunctions, ENTRANCES)
            )
        )
        val rangedEntrancesOrUnderground = DensityFunctions.rangeChoice(
            slopedCheese, -1000000.0, 1.5625, entrances, underground(densityFunctions, noiseParameters, slopedCheese)
        )
        val noodle = getFunction(densityFunctions, NOODLE)
        val finalDensity = DensityFunctions.min(
            postProcess(slideWastelands(rangedEntrancesOrUnderground)),
            DensityFunctions.constant(1.0)/*noodle*/
        )
        val yFunc = getFunction(densityFunctions, Y)
        val minVeinY = VeinType.entries.stream().mapToInt { vein -> vein.minY }.min().orElse(-DimensionType.MIN_Y * 2)
        val maxVeinY = VeinType.entries.stream().mapToInt { vein -> vein.maxY }.max().orElse(-DimensionType.MIN_Y * 2)
        val veininess = yLimitedInterpolatable(
            yFunc,
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEININESS), 1.5, 1.5),
            minVeinY,
            maxVeinY,
            0
        )
        val scale = 4.0
        val oreVeinA = yLimitedInterpolatable(
            yFunc,
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_A), scale, scale),
            minVeinY,
            maxVeinY,
            0
        ).abs()
        val oreVeinB = yLimitedInterpolatable(
            yFunc,
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_B), scale, scale),
            minVeinY,
            maxVeinY,
            0
        ).abs()
        val oreVeins = DensityFunctions.add(
            DensityFunctions.constant(-0.08), DensityFunctions.max(oreVeinA, oreVeinB)
        )
        val oreGap = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_GAP))
        val continents = getFunction(densityFunctions, NoiseRouterData.CONTINENTS)
        val erosion = getFunction(densityFunctions, NoiseRouterData.EROSION)
        val ridges = getFunction(densityFunctions, NoiseRouterData.RIDGES)
        return NoiseRouter(
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            slideOverworld(
                DensityFunctions.add(noiseGradient, DensityFunctions.constant(-0.703125)).clamp(-64.0, 64.0)
            ),
            finalDensity,
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero()
        )
    }

    fun overworld(
        densityFunctions: HolderGetter<DensityFunction>,
        noiseParameters: HolderGetter<NoiseParameters>
    ): NoiseRouter {
        val densityfunction = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5)
        val densityfunction1 =
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67)
        val densityfunction2 =
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143)
        val densityfunction3 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA))
        val densityfunction4 = getFunction(densityFunctions, SHIFT_X)
        val densityfunction5 = getFunction(densityFunctions, SHIFT_Z)
        val densityfunction6 = DensityFunctions.shiftedNoise2d(
            densityfunction4,
            densityfunction5,
            0.25,
            noiseParameters.getOrThrow(Noises.TEMPERATURE)
        )
        val densityfunction7 = DensityFunctions.shiftedNoise2d(
            densityfunction4,
            densityfunction5,
            0.25,
            noiseParameters.getOrThrow(Noises.VEGETATION)
        )
        val densityfunction8 = getFunction(
            densityFunctions,
            NoiseRouterData.FACTOR
        )
        val densityfunction9 = getFunction(
            densityFunctions,
            NoiseRouterData.DEPTH
        )
        val densityfunction10 =
            noiseGradientDensity(DensityFunctions.cache2d(densityfunction8), densityfunction9)
        val densityfunction11 = getFunction(
            densityFunctions,
            SLOPED_CHEESE
        )
        val densityfunction12 = DensityFunctions.min(
            densityfunction11,
            DensityFunctions.mul(
                DensityFunctions.constant(5.0),
                getFunction(densityFunctions, ENTRANCES)
            )
        )
        val densityfunction13 = DensityFunctions.rangeChoice(
            densityfunction11,
            -1000000.0,
            1.5625,
            densityfunction12,
            underground(densityFunctions, noiseParameters, densityfunction11)
        )
        val densityfunction14 = DensityFunctions.min(
            postProcess(
                slideOverworld(
                    densityfunction13
                )
            ), getFunction(densityFunctions, NOODLE)
        )
        val densityfunction15 = getFunction(densityFunctions, Y)
        val i = VeinType.entries.stream()
            .mapToInt { vein: VeinType -> vein.minY }
            .min().orElse(-DimensionType.MIN_Y * 2)
        val j = VeinType.entries.stream()
            .mapToInt { vein: VeinType -> vein.maxY }
            .max().orElse(-DimensionType.MIN_Y * 2)
        val densityfunction16 = yLimitedInterpolatable(
            densityfunction15,
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEININESS), 1.5, 1.5),
            i,
            j,
            0
        )
        val scale = 4.0
        val densityfunction17 = yLimitedInterpolatable(
            densityfunction15, DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_A), scale, scale), i, j, 0
        )
            .abs()
        val densityfunction18 = yLimitedInterpolatable(
            densityfunction15, DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_B), scale, scale), i, j, 0
        )
            .abs()
        val densityfunction19 = DensityFunctions.add(
            DensityFunctions.constant(-0.08),
            DensityFunctions.max(densityfunction17, densityfunction18)
        )
        val densityfunction20 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_GAP))
        return NoiseRouter(
            densityfunction,
            densityfunction1,
            densityfunction2,
            densityfunction3,
            densityfunction6,
            densityfunction7,
            getFunction(
                densityFunctions,
                NoiseRouterData.CONTINENTS
            ),
            getFunction(
                densityFunctions,
                NoiseRouterData.EROSION
            ),
            densityfunction9,
            getFunction(densityFunctions, NoiseRouterData.RIDGES),
            slideOverworld(
                DensityFunctions.add(densityfunction10, DensityFunctions.constant(-0.703125)).clamp(-64.0, 64.0)
            ),
            densityfunction14,
            densityfunction16,
            densityfunction19,
            densityfunction20
        )
    }
    

    fun none(): NoiseRouter {
        return NoiseRouter(
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero()
        )
    }

    private fun yLimitedInterpolatable(
        input: DensityFunction, whenInRange: DensityFunction, minY: Int, maxY: Int, whenOutOfRange: Int
    ): DensityFunction {
        return DensityFunctions.interpolated(
            DensityFunctions.rangeChoice(
                input,
                minY.toDouble(),
                (maxY + 1).toDouble(),
                whenInRange,
                DensityFunctions.constant(whenOutOfRange.toDouble())
            )
        )
    }

    private fun noiseGradientDensity(minFunction: DensityFunction, maxFunction: DensityFunction): DensityFunction {
        val minFunc = DensityFunctions.mul(maxFunction, minFunction)
        return DensityFunctions.mul(DensityFunctions.constant(4.0), minFunc.quarterNegative())
    }

    private fun underground(
        densityFunctions: HolderGetter<DensityFunction>,
        noiseParameters: HolderGetter<NoiseParameters>,
        p_256658_: DensityFunction
    ): DensityFunction {
        val densityfunction = getFunction(densityFunctions, SPAGHETTI_2D)
        val densityfunction1 = getFunction(densityFunctions, SPAGHETTI_ROUGHNESS_FUNCTION)
        val densityfunction2 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.CAVE_LAYER), 8.0)
        val densityfunction3 = DensityFunctions.mul(DensityFunctions.constant(4.0), densityfunction2.square())
        val densityfunction4 =
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666)
        val densityfunction5 = DensityFunctions.add(
            DensityFunctions.add(DensityFunctions.constant(0.27), densityfunction4).clamp(-1.0, 1.0),
            DensityFunctions.add(
                DensityFunctions.constant(1.5), DensityFunctions.mul(DensityFunctions.constant(-0.64), p_256658_)
            ).clamp(0.0, 0.5)
        )
        val densityfunction6 = DensityFunctions.add(densityfunction3, densityfunction5)
        val densityfunction7 = DensityFunctions.min(
            DensityFunctions.min(
                densityfunction6, getFunction(densityFunctions, ENTRANCES)
            ), DensityFunctions.add(densityfunction, densityfunction1)
        )
        val densityfunction8 = getFunction(densityFunctions, PILLARS)
        val densityfunction9 = DensityFunctions.rangeChoice(
            densityfunction8, -1000000.0, 0.03, DensityFunctions.constant(-1000000.0), densityfunction8
        )
        return DensityFunctions.max(densityfunction7, densityfunction9)
    }

    private fun postProcess(densityFunction: DensityFunction): DensityFunction {
        val blendedFunction = DensityFunctions.blendDensity(densityFunction)
        return DensityFunctions.mul(DensityFunctions.interpolated(blendedFunction), DensityFunctions.constant(0.64))
            .squeeze()
    }

    private fun slideWastelands(densityFunction: DensityFunction): DensityFunction {
        return slide(
            densityFunction,
            -64,
            384,
            80,
            64,
            -0.078125,
            0,
            24,
            0.1171875
        )
    }

    private fun slideOverworld(densityFunction: DensityFunction): DensityFunction {
        return slide(
            densityFunction,
            -64,
            384,
            80,
            64,
            -0.078125,
            0,
            24,
            0.1171875
        )
    }

    private fun slide(
        maxFunc: DensityFunction,
        minY: Int,
        maxY: Int,
        fromRange: Int,
        toRange: Int,
        min: Double,
        fromRange2: Int,
        toRange2: Int,
        min2: Double
    ): DensityFunction {
        val deltaFunc = DensityFunctions.yClampedGradient(minY + maxY - fromRange, minY + maxY - toRange, 1.0, 0.0)
        val max = DensityFunctions.lerp(deltaFunc, min, maxFunc)
        val clamp2 = DensityFunctions.yClampedGradient(minY + fromRange2, minY + toRange2, 0.0, 1.0)
        return DensityFunctions.lerp(clamp2, min2, max)
    }

    private fun getFunction(
        densityFunctions: HolderGetter<DensityFunction>, key: ResourceKey<DensityFunction>
    ): DensityFunction {
        return HolderHolder(densityFunctions.getOrThrow(key))
    }

    private fun createKey(location: String): ResourceKey<DensityFunction> {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.withDefaultNamespace(location))
    }

}