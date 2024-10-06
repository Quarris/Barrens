package dev.quarris.wastelands.terrablender

import com.mojang.datafixers.util.Pair
import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BiomeSetup
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Climate.ParameterPoint
import terrablender.api.ParameterUtils
import terrablender.api.ParameterUtils.Continentalness
import terrablender.api.ParameterUtils.Depth
import terrablender.api.ParameterUtils.Erosion
import terrablender.api.ParameterUtils.Humidity
import terrablender.api.ParameterUtils.ParameterPointListBuilder
import terrablender.api.ParameterUtils.Temperature
import terrablender.api.ParameterUtils.Weirdness
import terrablender.api.Region
import terrablender.api.RegionType
import terrablender.api.VanillaParameterOverlayBuilder
import java.util.function.Consumer


class WastelandsRegion : Region(
    ModRef.res("wastelands"),
    RegionType.OVERWORLD,
    7
) {

    override fun addBiomes(
        registry: Registry<Biome>,
        mapper: Consumer<Pair<ParameterPoint, ResourceKey<Biome>>>
    ) {
        val builder = VanillaParameterOverlayBuilder()
        ParameterPointListBuilder()
            .temperature(Temperature.HOT, Temperature.FROZEN)
            .humidity(Humidity.ARID)
            .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.MID_INLAND))
            .erosion(Erosion.span(Erosion.EROSION_0, Erosion.EROSION_3))
            .depth(Depth.SURFACE)
            .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING)
            .build().forEach(Consumer { point: ParameterPoint ->
                builder.add(
                    point,
                    BiomeSetup.WASTELANDS
                )
            })

        builder.build().forEach(mapper)
    }
}