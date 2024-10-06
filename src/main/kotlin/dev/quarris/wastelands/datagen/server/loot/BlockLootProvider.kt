package dev.quarris.wastelands.datagen.server.loot

import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.item.enchantment.LevelBasedValue
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator
import net.minecraft.world.level.storage.loot.providers.number.EnchantmentLevelProvider
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

class BlockLootProvider(
    registries: HolderLookup.Provider
) : BlockLootSubProvider(setOf(), FeatureFlags.DEFAULT_FLAGS, registries) {

    private val enchantments by lazy { registries.lookupOrThrow(Registries.ENCHANTMENT) }

    override fun generate() {

        dropSelf(BlockSetup.DRIED_DIRT.get())
        dropSelf(BlockSetup.DEAD_OAK_PLANKS.get())

        dropSelf(BlockSetup.DEAD_OAK_LOG.get())
        dropSelf(BlockSetup.DEAD_OAK_WOOD.get())
        dropSelf(BlockSetup.STRIPPED_DEAD_OAK_LOG.get())
        dropSelf(BlockSetup.STRIPPED_DEAD_OAK_WOOD.get())

        charredWood(BlockSetup.CHARRED_DEAD_OAK_LOG.get(), BlockSetup.DEAD_OAK_LOG.get(), 0.2f)
        charredWood(BlockSetup.CHARRED_DEAD_OAK_WOOD.get(), BlockSetup.DEAD_OAK_WOOD.get(), 0.2f)
        charredWood(BlockSetup.STRIPPED_CHARRED_DEAD_OAK_LOG.get(), BlockSetup.STRIPPED_DEAD_OAK_LOG.get(), 0.2f)
        charredWood(BlockSetup.STRIPPED_CHARRED_DEAD_OAK_WOOD.get(), BlockSetup.STRIPPED_DEAD_OAK_WOOD.get(), 0.2f)

        dropSelf(BlockSetup.DEAD_OAK_STAIRS.get())
        add(BlockSetup.DEAD_OAK_SLAB.get(), createSlabItemTable(BlockSetup.DEAD_OAK_SLAB.get()))
        dropSelf(BlockSetup.DEAD_OAK_FENCE.get())
        dropSelf(BlockSetup.DEAD_OAK_FENCE_GATE.get())
        dropSelf(BlockSetup.DEAD_OAK_PRESSURE_PLATE.get())
        dropSelf(BlockSetup.DEAD_OAK_BUTTON.get())
        add(BlockSetup.DEAD_OAK_DOOR.get(), createDoorTable(BlockSetup.DEAD_OAK_DOOR.get()))
        dropSelf(BlockSetup.DEAD_OAK_TRAPDOOR.get())

        add(
            BlockSetup.SLATE.get(), createSilkTouchOnlyTable(BlockSetup.SLATE.get()).withPool(
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
        return BlockSetup.REGISTRY.entries.stream().map { it.value() }.toList()
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
}