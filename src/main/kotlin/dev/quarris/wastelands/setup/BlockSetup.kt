package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import java.util.function.Function

object BlockSetup {

    val REGISTRY: DeferredRegister.Blocks = DeferredRegister.createBlocks(ModRef.ID)

    val DRIED_DIRT: DeferredBlock<Block> = registerBlockWithItem("dried_dirt", ::Block, Properties.of())
    val DEAD_WOOD_LOG: DeferredBlock<Block> = registerBlockWithItem("dead_wood_log", ::Block, Properties.of())
    val CHARRED_DEAD_WOOD_LOG: DeferredBlock<Block> = registerBlockWithItem("charred_dead_wood_log", ::Block, Properties.of())
    val STRIPPED_DEAD_WOOD_LOG: DeferredBlock<Block> = registerBlockWithItem("stripped_dead_wood_log", ::Block, Properties.of())
    val STRIPPED_CHARRED_DEAD_WOOD_LOG: DeferredBlock<Block> = registerBlockWithItem("stripped_charred_dead_wood_log", ::Block, Properties.of())

    private fun <B : Block> registerBlockWithItem(name: String, factory: Function<Properties, B>, properties: Properties) : DeferredBlock<B> {
        val block = REGISTRY.registerBlock(name, factory, properties)
        ItemSetup.REGISTRY.registerSimpleBlockItem(block)
        return block
    }

    fun init() {
        REGISTRY.register(MOD_BUS)
    }
}