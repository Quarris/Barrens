package dev.quarris.barrens.datagen.server.loot

import dev.quarris.barrens.block.PorousStoneBlock
import dev.quarris.barrens.setup.BlockSetup
import dev.quarris.barrens.setup.ItemSetup
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

class BlockLootProvider : BlockLootSubProvider(setOf(), FeatureFlags.DEFAULT_FLAGS) {

    override fun generate() {
        dropSelf(BlockSetup.DriedDirt.get())
        dropSelf(BlockSetup.DriedSand.get())
        dropSelf(BlockSetup.DriedSandstone.get())
        dropSelf(BlockSetup.DeadOakPlanks.get())
        dropSelf(BlockSetup.AncientOakSapling.get())

        dropSelf(BlockSetup.DeadOakLog.get())
        dropSelf(BlockSetup.DeadOakWood.get())
        dropSelf(BlockSetup.StrippedDeakOakLog.get())
        dropSelf(BlockSetup.StrippedDeadOakWood.get())

        charredWood(BlockSetup.CharredDeadOakLog.get(), BlockSetup.DeadOakLog.get(), 0.2f)
        charredWood(BlockSetup.CharredDeadOakWood.get(), BlockSetup.DeadOakWood.get(), 0.2f)
        charredWood(BlockSetup.StrippedCharredDeadOakLog.get(), BlockSetup.StrippedDeakOakLog.get(), 0.2f)
        charredWood(BlockSetup.StrippedCharredDeadOakWood.get(), BlockSetup.StrippedDeadOakWood.get(), 0.2f)

        dropSelf(BlockSetup.DeadOakStairs.get())
        add(BlockSetup.DeadOakSlab.get(), ::createSlabItemTable)
        dropSelf(BlockSetup.DeadOakFence.get())
        dropSelf(BlockSetup.DeadOakFenceGate.get())
        dropSelf(BlockSetup.DeadOakPressurePlate.get())
        dropSelf(BlockSetup.DeadOakButton.get())
        add(BlockSetup.DeadOakDoor.get(), ::createDoorTable)
        dropSelf(BlockSetup.DeadOakTrapdoor.get())

        add(BlockSetup.DriedShortGrass.get()) { block ->
            createGrassDrops(block, 0.1f).withPool(
                LootPool.lootPool()
                    .`when`(HAS_NO_SILK_TOUCH)
                    .add(
                        LootItem.lootTableItem(BlockSetup.AncientOakSapling.get())
                            .`when`(LootItemRandomChanceCondition.randomChance(0.05f))
                            .apply(
                                ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2)
                            )
                    )
            )
        }
        add(BlockSetup.DeadSeagrass.get(), ::createShearsOnlyDrop)
        add(BlockSetup.TallDeadSeagrass.get(), createDoublePlantShearsDrop(BlockSetup.DeadSeagrass.get()))

        add(
            BlockSetup.Slate.get(), createSilkTouchOnlyTable(BlockSetup.Slate.get()).withPool(
                LootPool.lootPool().`when`(HAS_NO_SILK_TOUCH).add(
                    LootItem.lootTableItem(Items.FLINT)
                        .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.5f)))
                        .apply(
                            ApplyBonusCount.addBonusBinomialDistributionCount(
                                Enchantments.BLOCK_FORTUNE, 0.8f, 0
                            )
                        )
                )
            )
        )

        BlockSetup.PorousStone.get().let { block ->
            add(block, createPorousStoneTable(block))
        }
    }

    override fun getKnownBlocks(): MutableIterable<Block> {
        return BlockSetup.Registry.entries.stream().map { it.get() }.toList()
    }

    private fun createPorousStoneTable(block: PorousStoneBlock): LootTable.Builder {
        return this.createSingleItemTableWithSilkTouch(
            block, Items.AIR
        ).withPool(
            LootPool.lootPool().`when`(HAS_NO_SILK_TOUCH)
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).add(
                    LootItem.lootTableItem(ItemSetup.RawIronNugget.get())
                        .`when`(LootItemRandomChanceCondition.randomChance(0.2f))
                ).add(
                    LootItem.lootTableItem(ItemSetup.RawGoldNugget.get())
                        .`when`(LootItemRandomChanceCondition.randomChance(0.05f))
                ).add(
                    LootItem.lootTableItem(ItemSetup.RawCopperNugget.get())
                        .`when`(LootItemRandomChanceCondition.randomChance(0.35f))
                        .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.5f)))
                )
        )
    }

    private fun charredWood(charred: Block, wood: Block, chance: Float) {
        add(
            charred, createSilkTouchDispatchTable(charred, LootItem.lootTableItem(wood)).withPool(
                LootPool.lootPool().`when`(HAS_NO_SILK_TOUCH).add(
                    LootItem.lootTableItem(Items.CHARCOAL)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                )
            )
        )
    }

    private fun createGrassDrops(block: Block, chance: Float): LootTable.Builder {
        return createShearsDispatchTable(
            block, applyExplosionDecay(
                block,
                LootItem.lootTableItem(Items.WHEAT_SEEDS).`when`(LootItemRandomChanceCondition.randomChance(chance))
                    .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))
            ) as LootPoolEntryContainer.Builder<*>
        )
    }
}