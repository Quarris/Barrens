package dev.quarris.barrens.terrablender

import com.mojang.datafixers.util.Pair
import dev.quarris.barrens.BarrensConfigs
import dev.quarris.barrens.ModRef
import dev.quarris.barrens.setup.BiomeSetup
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Climate.ParameterPoint
import terrablender.api.ParameterUtils.*
import terrablender.api.Region
import terrablender.api.RegionType
import terrablender.api.VanillaParameterOverlayBuilder
import java.util.function.Consumer


class BarrensRegion : Region(
    ModRef.res("barrens"),
    RegionType.OVERWORLD,
    BarrensConfigs.regionWeight
) {

    private fun biome(
        biome: ResourceKey<Biome>,
        mapper: Consumer<Pair<ParameterPoint, ResourceKey<Biome>>>,
        paramBuilder: Consumer<ParameterPointListBuilder>
    ) {
        VanillaParameterOverlayBuilder().let { builder ->
            val params = ParameterPointListBuilder()
                .temperature(Temperature.HOT, Temperature.FROZEN)
                .humidity(Humidity.ARID)
            paramBuilder.accept(params)

            params.build().forEach(Consumer { point: ParameterPoint ->
                builder.add(
                    point,
                    biome
                )
            })
            builder.build().forEach(mapper)
        }
    }

    override fun addBiomes(
        registry: Registry<Biome>,
        mapper: Consumer<Pair<ParameterPoint, ResourceKey<Biome>>>
    ) {
        biome(BiomeSetup.Wasteland, mapper) { params ->
            params.continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_2, Erosion.EROSION_6))
                .depth(Depth.FULL_RANGE)
                .weirdness(
                    Weirdness.LOW_SLICE_VARIANT_ASCENDING,
                    Weirdness.LOW_SLICE_NORMAL_DESCENDING,
                    Weirdness.MID_SLICE_NORMAL_ASCENDING,
                    Weirdness.MID_SLICE_NORMAL_DESCENDING,
                    Weirdness.MID_SLICE_VARIANT_ASCENDING,
                    Weirdness.MID_SLICE_VARIANT_DESCENDING,
                    Weirdness.HIGH_SLICE_NORMAL_ASCENDING,
                    Weirdness.HIGH_SLICE_NORMAL_DESCENDING,
                    Weirdness.HIGH_SLICE_VARIANT_ASCENDING,
                    Weirdness.HIGH_SLICE_VARIANT_DESCENDING,
                )
        }

        biome(BiomeSetup.WastelandShore, mapper) { params ->
            params.continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_4, Erosion.EROSION_6))
                .depth(Depth.FULL_RANGE)
                .weirdness(
                    Weirdness.LOW_SLICE_VARIANT_ASCENDING,
                    Weirdness.LOW_SLICE_NORMAL_DESCENDING,
                    Weirdness.MID_SLICE_NORMAL_ASCENDING,
                    Weirdness.MID_SLICE_NORMAL_DESCENDING,
                    Weirdness.MID_SLICE_VARIANT_ASCENDING,
                    Weirdness.MID_SLICE_VARIANT_DESCENDING,
                )
        }


        biome(BiomeSetup.DeadOcean, mapper) { params ->
            params.continentalness(Continentalness.span(Continentalness.DEEP_OCEAN, Continentalness.OCEAN))
                .erosion(Erosion.FULL_RANGE)
                .depth(Depth.FULL_RANGE)
                .weirdness(Weirdness.FULL_RANGE)
        }
    }
}