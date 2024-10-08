package dev.quarris.wastelands.datagen.server.loot

import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
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

class BlockLootProvider(
    registries: HolderLookup.Provider
) : BlockLootSubProvider(setOf(), FeatureFlags.DEFAULT_FLAGS, registries) {

    private val enchantments by lazy { registries.lookupOrThrow(Registries.ENCHANTMENT) }

    override fun generate() {
        dropSelf(BlockSetup.DriedDirt.get())
        dropSelf(BlockSetup.DriedSand.get())
        dropSelf(BlockSetup.DriedSandstone.get())
        dropSelf(BlockSetup.DeadOakPlanks.get())

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

        add(BlockSetup.DriedShortGrass.get()) { block -> createGrassDrops(block, 0.05f) }
        add(BlockSetup.DeadSeagrass.get(), ::createShearsOnlyDrop)
        add(BlockSetup.TallDeadSeagrass.get(), createDoublePlantShearsDrop(BlockSetup.DeadSeagrass.get()))

        add(
            BlockSetup.Slate.get(), createSilkTouchOnlyTable(BlockSetup.Slate.get()).withPool(
                LootPool.lootPool()
                    .`when`(doesNotHaveSilkTouch())
                    .add(
                        LootItem.lootTableItem(Items.FLINT)
                            .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.5f)))
                            .apply(ApplyBonusCount.addBonusBinomialDistributionCount(enchantments.getOrThrow(Enchantments.FORTUNE), 0.8f, 0))
                    )
            )
        )
    }

    override fun getKnownBlocks(): MutableIterable<Block> {
        return BlockSetup.Registry.entries.stream().map { it.value() }.toList()
    }

    private fun charredWood(charred: Block, wood: Block, chance: Float) {
        val enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT)
        add(
            charred, createSilkTouchDispatchTable(charred, LootItem.lootTableItem(wood)).withPool(
                LootPool.lootPool()
                    .`when`(doesNotHaveSilkTouch())
                    .add(
                        LootItem.lootTableItem(Items.CHARCOAL)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                            .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                    )
            )
        )
    }

    private fun createGrassDrops(block: Block, chance: Float): LootTable.Builder {
        val enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT)
        return createShearsDispatchTable(
            block,
            applyExplosionDecay(
                block,
                LootItem.lootTableItem(Items.WHEAT_SEEDS)
                    .`when`(LootItemRandomChanceCondition.randomChance(chance))
                    .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE), 2))
            ) as LootPoolEntryContainer.Builder<*>
        )
    }
}