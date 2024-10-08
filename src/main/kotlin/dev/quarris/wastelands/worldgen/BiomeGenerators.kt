package dev.quarris.wastelands.worldgen

import dev.quarris.wastelands.setup.OrePlacementSetup
import dev.quarris.wastelands.setup.PlacedFeatureSetup
import net.minecraft.core.HolderGetter
import net.minecraft.data.worldgen.BiomeDefaultFeatures
import net.minecraft.data.worldgen.placement.CavePlacements
import net.minecraft.data.worldgen.placement.OrePlacements
import net.minecraft.sounds.Musics
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.BiomeGenerationSettings
import net.minecraft.world.level.biome.BiomeSpecialEffects
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object BiomeGenerators {

    fun wasteland(
        placedFeatures: HolderGetter<PlacedFeature>,
        worldCarvers: HolderGetter<ConfiguredWorldCarver<*>>
    ): Biome {
        // Mob Settings
        val mobSettings = MobSpawnSettings.Builder()
        BiomeDefaultFeatures.monsters(mobSettings, 20, 2, 9, false)

        // Features
        val generationSettings = BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)

        generationSettings
            .addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureSetup.DriedWaterLake)
            .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PlacedFeatureSetup.SlateBoulder)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureSetup.DriedGrassPatch)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureSetup.DeadOakTree)

        addWastelandDisks(generationSettings)
        addWastelandOres(generationSettings)
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings)
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings)
        addUndergroundVariety(generationSettings)
        BiomeDefaultFeatures.addDefaultSprings(generationSettings)
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings)
        BiomeDefaultFeatures.addDripstone(generationSettings)
        BiomeDefaultFeatures.addFossilDecoration(generationSettings)

        return Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(-1.6f)
            .specialEffects(
                BiomeSpecialEffects.Builder()
                    .waterColor(0x4a4728)
                    .waterFogColor(0x3b3821)
                    .fogColor(0x696754)
                    .skyColor(0x5e5d4b)
                    .grassColorOverride(0x85a322)
                    .foliageColorOverride(0x85a322)
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
            .mobSpawnSettings(mobSettings.build())
            .generationSettings(generationSettings.build())
            .build()
    }

    fun wastelandCoast(
        placedFeatures: HolderGetter<PlacedFeature>,
        worldCarvers: HolderGetter<ConfiguredWorldCarver<*>>
    ): Biome {
        // Mob Settings
        val mobSettings = MobSpawnSettings.Builder()
        BiomeDefaultFeatures.monsters(mobSettings, 20, 2, 9, false)

        // Features
        val generationSettings = BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)

        generationSettings
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureSetup.DriedGrassPatch)

        addWastelandDisks(generationSettings)
        addWastelandOres(generationSettings)
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings)
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings)
        addUndergroundVariety(generationSettings)
        BiomeDefaultFeatures.addDefaultSprings(generationSettings)
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings)
        BiomeDefaultFeatures.addDripstone(generationSettings)
        BiomeDefaultFeatures.addFossilDecoration(generationSettings)

        return Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(1.3f)
            .downfall(-1.6f)
            .specialEffects(
                BiomeSpecialEffects.Builder()
                    .waterColor(0x4a4728)
                    .waterFogColor(0x3b3821)
                    .fogColor(0x696754)
                    .skyColor(0x5e5d4b)
                    .grassColorOverride(0x85a322)
                    .foliageColorOverride(0x85a322)
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
            .mobSpawnSettings(mobSettings.build())
            .generationSettings(generationSettings.build())
            .build()
    }

    fun deadOcean(
        placedFeatures: HolderGetter<PlacedFeature>,
        worldCarvers: HolderGetter<ConfiguredWorldCarver<*>>
    ): Biome {
        // Mob Settings
        val mobSettings = MobSpawnSettings.Builder()
        BiomeDefaultFeatures.monsters(mobSettings, 20, 2, 9, false)
        mobSettings.addSpawn(MobCategory.AXOLOTLS, SpawnerData(EntityType.AXOLOTL, 10, 1, 2))
        mobSettings.addSpawn(MobCategory.WATER_AMBIENT, SpawnerData(EntityType.PUFFERFISH, 25, 8, 8))

        // Features
        val generationSettings = BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)

        addWastelandDisks(generationSettings)
        addWastelandOres(generationSettings)
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings)
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings)
        addUndergroundVariety(generationSettings)
        BiomeDefaultFeatures.addDefaultSprings(generationSettings)
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings)
        BiomeDefaultFeatures.addDripstone(generationSettings)
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureSetup.DeadSeagrassPatch)

        return Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(1.3f)
            .downfall(-1.6f)
            .specialEffects(
                BiomeSpecialEffects.Builder()
                    .waterColor(0x38361f)
                    .waterFogColor(0x2b2a19)
                    .fogColor(0x696754)
                    .skyColor(0x5e5d4b)
                    .grassColorOverride(0x85a322)
                    .foliageColorOverride(0x85a322)
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
            .mobSpawnSettings(mobSettings.build())
            .generationSettings(generationSettings.build())
            .build()
    }

    // Adds wasteland disks
    private fun addWastelandDisks(builder: BiomeGenerationSettings.Builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureSetup.DriedSandDisk)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureSetup.GravelDisk)
    }

    // Adds all ores present in wasteland biome
    private fun addWastelandOres(builder: BiomeGenerationSettings.Builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacementSetup.CoalVeinLower)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacementSetup.CoalVeinUpper)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacementSetup.LargeCoalVein)
    }

    // Adds the default variety excluding dirt
    private fun addUndergroundVariety(builder: BiomeGenerationSettings.Builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRAVEL)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_UPPER)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_UPPER)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_UPPER)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER)
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF)
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN)
    }

}