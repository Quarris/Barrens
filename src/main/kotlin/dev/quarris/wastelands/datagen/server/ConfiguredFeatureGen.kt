package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.ConfiguredFeatureSetup
import dev.quarris.wastelands.setup.FeatureSetup
import dev.quarris.wastelands.setup.OreFeatureSetup
import net.minecraft.core.Direction
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.tags.BlockTags
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.LakeFeature
import net.minecraft.world.level.levelgen.feature.configurations.*
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest

object ConfiguredFeatureGen : RegistrySetBuilder.RegistryBootstrap<ConfiguredFeature<*, *>> {
    override fun run(context: BootstrapContext<ConfiguredFeature<*, *>>) {

        val baseStoneOverworld: RuleTest = TagMatchTest(BlockTags.BASE_STONE_OVERWORLD)

        registerOres(context)

        context.register(
            ConfiguredFeatureSetup.DeadOakTree,
            ConfiguredFeature(FeatureSetup.DeadOakTree.get(), ProbabilityFeatureConfiguration(0.7f))
        )
        context.register(
            ConfiguredFeatureSetup.SlateBoulder,
            ConfiguredFeature(
                FeatureSetup.Boulder.get(),
                BlockStateConfiguration(BlockSetup.Slate.get().defaultBlockState())
            )
        )
        context.register(
            ConfiguredFeatureSetup.DriedGrass,
            ConfiguredFeature(
                Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(
                    12,
                    PlacementUtils.onlyWhenEmpty<SimpleBlockConfiguration, Feature<SimpleBlockConfiguration>>(
                        Feature.SIMPLE_BLOCK,
                        SimpleBlockConfiguration(BlockStateProvider.simple(BlockSetup.DriedShortGrass.get()))
                    )
                )
            )
        )

        context.register(
            ConfiguredFeatureSetup.SingleDriedGrass,
            ConfiguredFeature(
                Feature.SIMPLE_BLOCK,
                SimpleBlockConfiguration(
                    BlockStateProvider.simple(
                        BlockSetup.DriedShortGrass.get().defaultBlockState()
                    )
                )
            )
        )

        context.register(
            ConfiguredFeatureSetup.DriedWaterLake,
            ConfiguredFeature(
                Feature.LAKE,
                LakeFeature.Configuration(
                    BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
                    BlockStateProvider.simple(BlockSetup.DriedSand.get().defaultBlockState())
                )
            )
        )

        context.register(
            ConfiguredFeatureSetup.DeadSeagrassSingle,
            ConfiguredFeature(
                Feature.SIMPLE_BLOCK,
                SimpleBlockConfiguration(BlockStateProvider.simple(BlockSetup.DeadSeagrass.get()))
            )
        )

        context.register(
            ConfiguredFeatureSetup.DeadSeagrass,
            ConfiguredFeature(
                FeatureSetup.DeadSeagrass.get(),
                ProbabilityFeatureConfiguration(0.15f)
            )
        )

        FeatureUtils.register(
            context,
            ConfiguredFeatureSetup.GravelDisk,
            Feature.DISK,
            DiskConfiguration(
                RuleBasedBlockStateProvider.simple(Blocks.GRAVEL),
                BlockPredicate.matchesBlocks(BlockSetup.DriedDirt.get(), BlockSetup.DriedSand.get()),
                UniformInt.of(2, 5),
                2
            )
        )

        FeatureUtils.register(
            context,
            ConfiguredFeatureSetup.DriedSandDisk,
            Feature.DISK,
            DiskConfiguration(
                RuleBasedBlockStateProvider(
                    BlockStateProvider.simple(BlockSetup.DriedSand.get()),
                    listOf(
                        RuleBasedBlockStateProvider.Rule(
                            BlockPredicate.matchesBlocks(Direction.DOWN.normal, Blocks.AIR),
                            BlockStateProvider.simple(BlockSetup.DriedSandstone.get())
                        )
                    )
                ),
                BlockPredicate.matchesBlocks(BlockSetup.DriedDirt.get()),
                UniformInt.of(2, 6),
                2
            )
        )

        FeatureUtils.register(
            context,
            ConfiguredFeatureSetup.PorousStoneBlob,
            Feature.ORE,
            OreConfiguration(baseStoneOverworld, BlockSetup.PorousStone.get().defaultBlockState(), 64)
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
            OreFeatureSetup.CoalVein,
            ConfiguredFeature(Feature.ORE, OreConfiguration(coalRules, 16, 0.05f))
        )

        context.register(
            OreFeatureSetup.LargeCoalVein,
            ConfiguredFeature(Feature.ORE, OreConfiguration(coalRules, 38, 0.1f))
        )
    }


}