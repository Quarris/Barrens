@file:Suppress("UNCHECKED_CAST")

package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.block.WoodBlock
import net.minecraft.core.Direction
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import java.util.function.Function
import java.util.function.Supplier

object BlockSetup {

    val REGISTRY: DeferredRegister.Blocks = DeferredRegister.createBlocks(ModRef.ID)

    val DRIED_DIRT: DeferredBlock<Block> = registerBlockWithItem(
        "dried_dirt", ::Block
    ) {
        Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
            .strength(1.2F, 2.0F)
    }

    val DEAD_OAK_LOG: DeferredBlock<RotatedPillarBlock> =
        registerBlockWithItem("dead_oak_log", { props -> WoodBlock(props, STRIPPED_DEAD_OAK_LOG) }) {
            log(
                MapColor.COLOR_LIGHT_GRAY, MapColor.COLOR_GRAY
            )
        }

    val DEAD_OAK_WOOD: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "dead_oak_wood",
        { props -> WoodBlock(props, STRIPPED_DEAD_OAK_WOOD) }) { Properties.ofFullCopy(DEAD_OAK_LOG.get()) }

    val STRIPPED_DEAD_OAK_LOG: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_dead_oak_log", ::RotatedPillarBlock
    ) { Properties.ofFullCopy(DEAD_OAK_LOG.get()) }

    val STRIPPED_DEAD_OAK_WOOD: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_dead_oak_wood", ::RotatedPillarBlock
    ) { Properties.ofFullCopy(DEAD_OAK_LOG.get()) }

    val CHARRED_DEAD_OAK_LOG: DeferredBlock<RotatedPillarBlock> =
        registerBlockWithItem("charred_dead_oak_log", { props -> WoodBlock(props, STRIPPED_CHARRED_DEAD_OAK_LOG) }) {
            Properties.ofFullCopy(
                DEAD_OAK_LOG.get()
            )
        }

    val CHARRED_DEAD_OAK_WOOD: DeferredBlock<RotatedPillarBlock> =
        registerBlockWithItem("charred_dead_oak_wood", { props -> WoodBlock(props, STRIPPED_CHARRED_DEAD_OAK_WOOD) }) {
            Properties.ofFullCopy(
                DEAD_OAK_LOG.get()
            )
        }

    val STRIPPED_CHARRED_DEAD_OAK_LOG: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_charred_dead_oak_log", ::RotatedPillarBlock
    ) { Properties.ofFullCopy(DEAD_OAK_LOG.get()) }

    val STRIPPED_CHARRED_DEAD_OAK_WOOD: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_charred_dead_oak_wood", ::RotatedPillarBlock
    ) { Properties.ofFullCopy(DEAD_OAK_LOG.get()) }

    val DEAD_OAK_PLANKS: DeferredBlock<Block> = registerBlockWithItem(
        "dead_oak_planks", ::Block
    ) {
        Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F)
            .sound(SoundType.WOOD).ignitedByLava()
    }

    val DEAD_OAK_SLAB: DeferredBlock<SlabBlock> =
        registerBlockWithItem("dead_oak_slab", ::SlabBlock) { Properties.ofFullCopy(DEAD_OAK_PLANKS.get()) }

    val DEAD_OAK_STAIRS: DeferredBlock<StairBlock> = registerBlockWithItem("dead_oak_stairs",
        { props -> StairBlock(DEAD_OAK_PLANKS.get().defaultBlockState(), props) },
        { Properties.ofFullCopy(DEAD_OAK_PLANKS.get()) })
    val DEAD_OAK_FENCE: DeferredBlock<FenceBlock> = registerBlockWithItem(
        "dead_oak_fence", ::FenceBlock
    ) {
        Properties.of().mapColor(DEAD_OAK_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS)
            .strength(2.0f, 3.0f).ignitedByLava().sound(SoundType.WOOD)
    }
    val DEAD_OAK_FENCE_GATE: DeferredBlock<FenceGateBlock> =
        registerBlockWithItem("dead_oak_fence_gate", { props -> FenceGateBlock(WoodTypeSetup.DEAD_OAK, props) }, {
            Properties.of().mapColor(DEAD_OAK_PLANKS.get().defaultMapColor()).forceSolidOn()
                .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).ignitedByLava()
        })
    val DEAD_OAK_PRESSURE_PLATE: DeferredBlock<PressurePlateBlock> = registerBlockWithItem("dead_oak_pressure_plate",
        { props -> PressurePlateBlock(BlockSetSetup.DEAD_OAK, props) },
        {
            Properties.of().mapColor(DEAD_OAK_PLANKS.get().defaultMapColor()).forceSolidOn()
                .instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5f).ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
        })
    val DEAD_OAK_BUTTON: DeferredBlock<ButtonBlock> =
        registerBlockWithItem("dead_oak_button", { props -> ButtonBlock(BlockSetSetup.DEAD_OAK, 30, props) }, {
            Properties.of().noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY)
        })

    val DEAD_OAK_DOOR: DeferredBlock<DoorBlock> =
        registerBlockWithItem("dead_oak_door", { props -> DoorBlock(BlockSetSetup.DEAD_OAK, props) }) {
            Properties.of()
                .mapColor(DEAD_OAK_PLANKS.get().defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0f)
                .noOcclusion()
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
        }

    val DEAD_OAK_TRAPDOOR: DeferredBlock<TrapDoorBlock> =
        registerBlockWithItem("dead_oak_trapdoor", { props -> TrapDoorBlock(BlockSetSetup.DEAD_OAK, props) }) {
            Properties.of()
                .mapColor(DEAD_OAK_PLANKS.get().defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0f)
                .noOcclusion()
                .isValidSpawn { state, blockGetter, pos, entity -> Blocks.never(state, blockGetter, pos, entity) }
                .ignitedByLava()
        }

    private fun <B : Block> registerBlockWithItem(
        name: String, factory: Function<Properties, B>, properties: Supplier<Properties>
    ): DeferredBlock<B> {
        val block: DeferredBlock<B> = REGISTRY.register(name, Supplier<B> { factory.apply(properties.get()) })
        ItemSetup.REGISTRY.registerSimpleBlockItem(block)
        return block
    }

    fun init() {
        REGISTRY.register(MOD_BUS)
    }

    private fun log(topMapColor: MapColor, sideMapColor: MapColor): Properties {
        return Properties.of().mapColor { state: BlockState ->
            if (state.getValue(RotatedPillarBlock.AXIS) === Direction.Axis.Y) topMapColor
            else sideMapColor
        }.instrument(NoteBlockInstrument.BASS).strength(2.0f).sound(SoundType.WOOD).ignitedByLava()
    }
}