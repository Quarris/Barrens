package dev.quarris.wastelands.worldgen

import net.minecraft.core.HolderGetter
import net.minecraft.data.worldgen.Carvers
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements
import net.minecraft.sounds.Musics
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.BiomeGenerationSettings
import net.minecraft.world.level.biome.BiomeSpecialEffects
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object BiomeGenerators {

    fun wasteland(
        placedFeatures: HolderGetter<PlacedFeature>,
        worldCarvers: HolderGetter<ConfiguredWorldCarver<*>>
    ): Biome {
        val mobSettings = MobSpawnSettings.Builder()
            .build()
        val generationSettings = BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
            .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_LAVA)

        //BiomeDefaultFeatures.addDefaultMushrooms(generationSettings)
        //generationSettings.addFeature(
        //    GenerationStep.Decoration.UNDERGROUND_DECORATION,
        //    NetherPlacements.SPRING_OPEN
        //)
        //.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
        //.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE)
        //.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
        //.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
        //.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER)
        //.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER)
        //.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
        //.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED)
        //BiomeDefaultFeatures.addNetherDefaultOres(generationSettings)

        return Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(1.3f)
            .downfall(-1.6f)
            .specialEffects(
                BiomeSpecialEffects.Builder()
                    .waterColor(0xff4a4728.toInt())
                    .waterFogColor(0xff3b3821.toInt())
                    .fogColor(0x696754)
                    .skyColor(0x5e5d4b)
                    .grassColorOverride(0x7f8c53)
                    //.ambientLoopSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
                    //.ambientMoodSound(AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0))
                    //.ambientAdditionsSound(
                    //    AmbientAdditionsSettings(
                    //        SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS,
                    //        0.0111
                    //    )
                    //)
                    .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SWAMP))
                    .build()
            )
            .mobSpawnSettings(mobSettings)
            .generationSettings(generationSettings.build())
            .build()
    }

}