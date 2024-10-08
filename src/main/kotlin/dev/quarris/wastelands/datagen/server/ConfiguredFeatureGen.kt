package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.ConfiguredFeatureSetup
import dev.quarris.wastelands.setup.FeatureSetup
import dev.quarris.wastelands.setup.OreFeatureSetup
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.LakeFeature
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest

object ConfiguredFeatureGen : RegistrySetBuilder.RegistryBootstrap<ConfiguredFeature<*, *>> {
    override fun run(context: BootstrapContext<ConfiguredFeature<*, *>>) {
        registerOres(context)

        context.register(
            ConfiguredFeatureSetup.DEAD_OAK_TREE,
            ConfiguredFeature(FeatureSetup.DEAD_OAK_TREE.get(), ProbabilityFeatureConfiguration(0.7f))
        )
        context.register(
            ConfiguredFeatureSetup.SLATE_BOULDER,
            ConfiguredFeature(
                FeatureSetup.BOULDER.get(),
                BlockStateConfiguration(BlockSetup.SLATE.get().defaultBlockState())
            )
        )
        context.register(
            ConfiguredFeatureSetup.DRIED_GRASS,
            ConfiguredFeature(
                Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(
                    12,
                    PlacementUtils.onlyWhenEmpty<SimpleBlockConfiguration, Feature<SimpleBlockConfiguration>>(
                        Feature.SIMPLE_BLOCK,
                        SimpleBlockConfiguration(BlockStateProvider.simple(BlockSetup.DRIED_SHORT_GRASS.get()))
                    )
                )
            )
        )

        context.register(
            ConfiguredFeatureSetup.SINGLE_DRIED_GRASS,
            ConfiguredFeature(
                Feature.SIMPLE_BLOCK,
                SimpleBlockConfiguration(BlockStateProvider.simple(BlockSetup.DRIED_SHORT_GRASS.get().defaultBlockState()))
            )
        )

        context.register(
            ConfiguredFeatureSetup.DRIED_DIRT_WATER_LAKE,
            ConfiguredFeature(
                Feature.LAKE,
                LakeFeature.Configuration(
                    BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
                    BlockStateProvider.simple(BlockSetup.DRIED_DIRT.get().defaultBlockState())
                )
            )
        )

        context.register(
            ConfiguredFeatureSetup.DEAD_SEAGRASS_SINGLE,
            ConfiguredFeature(
                Feature.SIMPLE_BLOCK,
                SimpleBlockConfiguration(BlockStateProvider.simple(BlockSetup.DEAD_SEAGRASS.get()))
            )
        )

        context.register(
            ConfiguredFeatureSetup.DEAD_SEAGRASS,
            ConfiguredFeature(
                FeatureSetup.DEAD_SEAGRASS.get(),
                ProbabilityFeatureConfiguration(0.15f)
            )
        )
    }

    private fun registerOres(context: BootstrapContext<ConfiguredFeature<*, *>>) {
        val baseOverworldStones: RuleTest = TagMatchTest(BlockTags.BASE_STONE_OVERWORLD)
        val replaceableStones: RuleTest = TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES)
        val replaceableDeepslate: RuleTest = TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
        val isNetherrack: RuleTest = BlockMatchTest(Blocks.NETHERRACK)
        val baseNetherStones: RuleTest = TagMatchTest(BlockTags.BASE_STONE_NETHER)
        val ironRules = listOf(
            OreConfiguration.target(replaceableStones, Blocks.IRON_ORE.defaultBlockState()),
            OreConfiguration.target(replaceableDeepslate, Blocks.DEEPSLATE_IRON_ORE.defaultBlockState())
        )
        val goldRules = listOf(
            OreConfiguration.target(replaceableStones, Blocks.GOLD_ORE.defaultBlockState()),
            OreConfiguration.target(replaceableDeepslate, Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState())
        )
        val diamondRules = listOf(
            OreConfiguration.target(replaceableStones, Blocks.DIAMOND_ORE.defaultBlockState()),
            OreConfiguration.target(replaceableDeepslate, Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState())
        )
        val lapisRules = listOf(
            OreConfiguration.target(replaceableStones, Blocks.LAPIS_ORE.defaultBlockState()),
            OreConfiguration.target(replaceableDeepslate, Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState())
        )
        val copperRules = listOf(
            OreConfiguration.target(replaceableStones, Blocks.COPPER_ORE.defaultBlockState()),
            OreConfiguration.target(replaceableDeepslate, Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState())
        )
        val coalRules = listOf(
            OreConfiguration.target(replaceableStones, Blocks.COAL_ORE.defaultBlockState()),
            OreConfiguration.target(replaceableDeepslate, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState())
        )

        context.register(
            OreFeatureSetup.COAL_VEIN,
            ConfiguredFeature(Feature.ORE, OreConfiguration(coalRules, 16, 0.05f))
        )

        context.register(
            OreFeatureSetup.LARGE_COAL_VEIN,
            ConfiguredFeature(Feature.ORE, OreConfiguration(coalRules, 38, 0.1f))
        )
    }


}