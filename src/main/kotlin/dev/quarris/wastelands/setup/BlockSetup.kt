package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.block.*
import net.minecraft.core.Direction
import net.minecraft.util.ColorRGBA
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour
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

    val Registry: DeferredRegister.Blocks = DeferredRegister.createBlocks(ModRef.ID)

    val DriedDirt: DeferredBlock<DriedDirtBlock> = registerBlockWithItem(
        "dried_dirt", ::DriedDirtBlock
    ) {
        Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.2F, 2.0F)
    }

    val DriedSand: DeferredBlock<Block> = registerBlockWithItem(
        "dried_sand", { props ->
            ColoredFallingBlock(
                ColorRGBA(0x5D5941),
                props
            )
        }
    ) {
        Properties.of().mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.SNARE)
            .sound(SoundType.SAND)
            .strength(0.6F)
    }

    val DriedSandstone: DeferredBlock<Block> = registerBlockWithItem(
        "dried_sandstone", ::DriedDirtBlock
    ) {
        Properties.of().mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.BASS)
            .requiresCorrectToolForDrops()
            .strength(0.8F)
    }

    val PorousStone: DeferredBlock<PorousStoneBlock> = registerBlockWithItem(
        "porous_stone", ::PorousStoneBlock
    ) {
        Properties.of().mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.2F)
    }

    val DeadOakLog: DeferredBlock<RotatedPillarBlock> =
        registerBlockWithItem("dead_oak_log", { props -> WoodBlock(props, StrippedDeakOakLog) }) {
            log(
                MapColor.COLOR_LIGHT_GRAY, MapColor.COLOR_GRAY
            )
        }

    val DeadOakWood: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "dead_oak_wood",
        { props -> WoodBlock(props, StrippedDeadOakWood) }) { Properties.ofFullCopy(DeadOakLog.get()) }

    val StrippedDeakOakLog: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_dead_oak_log", ::RotatedPillarBlock
    ) { Properties.ofFullCopy(DeadOakLog.get()) }

    val StrippedDeadOakWood: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_dead_oak_wood", ::RotatedPillarBlock
    ) { Properties.ofFullCopy(DeadOakLog.get()) }

    val CharredDeadOakLog: DeferredBlock<RotatedPillarBlock> =
        registerBlockWithItem("charred_dead_oak_log", { props -> WoodBlock(props, StrippedCharredDeadOakLog) }) {
            Properties.ofFullCopy(
                DeadOakLog.get()
            )
        }

    val CharredDeadOakWood: DeferredBlock<RotatedPillarBlock> =
        registerBlockWithItem("charred_dead_oak_wood", { props -> WoodBlock(props, StrippedCharredDeadOakWood) }) {
            Properties.ofFullCopy(
                DeadOakLog.get()
            )
        }

    val StrippedCharredDeadOakLog: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_charred_dead_oak_log", ::RotatedPillarBlock
    ) { Properties.ofFullCopy(DeadOakLog.get()) }

    val StrippedCharredDeadOakWood: DeferredBlock<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_charred_dead_oak_wood", ::RotatedPillarBlock
    ) { Properties.ofFullCopy(DeadOakLog.get()) }

    val DeadOakPlanks: DeferredBlock<Block> = registerBlockWithItem(
        "dead_oak_planks", ::Block
    ) {
        Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASS).strength(1.0F)
            .sound(SoundType.WOOD).ignitedByLava()
    }

    val DeadOakSlab: DeferredBlock<SlabBlock> =
        registerBlockWithItem("dead_oak_slab", ::SlabBlock) { Properties.ofFullCopy(DeadOakPlanks.get()) }

    val DeadOakStairs: DeferredBlock<StairBlock> = registerBlockWithItem("dead_oak_stairs",
        { props -> StairBlock(DeadOakPlanks.get().defaultBlockState(), props) },
        { Properties.ofFullCopy(DeadOakPlanks.get()) })
    val DeadOakFence: DeferredBlock<FenceBlock> = registerBlockWithItem(
        "dead_oak_fence", ::FenceBlock
    ) {
        Properties.of().mapColor(DeadOakPlanks.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS)
            .strength(2.0f, 3.0f).ignitedByLava().sound(SoundType.WOOD)
    }
    val DeadOakFenceGate: DeferredBlock<FenceGateBlock> =
        registerBlockWithItem("dead_oak_fence_gate", { props -> FenceGateBlock(WoodTypeSetup.DeadOak, props) }, {
            Properties.of().mapColor(DeadOakPlanks.get().defaultMapColor()).forceSolidOn()
                .instrument(NoteBlockInstrument.BASS).strength(1.0F).ignitedByLava()
        })
    val DeadOakPressurePlate: DeferredBlock<PressurePlateBlock> = registerBlockWithItem("dead_oak_pressure_plate",
        { props -> PressurePlateBlock(BlockSetSetup.DeadOak, props) },
        {
            Properties.of().mapColor(DeadOakPlanks.get().defaultMapColor()).forceSolidOn()
                .instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5f).ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
        })
    val DeadOakButton: DeferredBlock<ButtonBlock> =
        registerBlockWithItem("dead_oak_button", { props -> ButtonBlock(BlockSetSetup.DeadOak, 30, props) }, {
            Properties.of().noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY)
        })

    val DeadOakDoor: DeferredBlock<DoorBlock> =
        registerBlockWithItem("dead_oak_door", { props -> DoorBlock(BlockSetSetup.DeadOak, props) }) {
            Properties.of()
                .mapColor(DeadOakPlanks.get().defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .noOcclusion()
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
        }

    val DeadOakTrapdoor: DeferredBlock<TrapDoorBlock> =
        registerBlockWithItem("dead_oak_trapdoor", { props -> TrapDoorBlock(BlockSetSetup.DeadOak, props) }) {
            Properties.of()
                .mapColor(DeadOakPlanks.get().defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0f)
                .noOcclusion()
                .isValidSpawn { state, blockGetter, pos, entity -> Blocks.never(state, blockGetter, pos, entity) }
                .ignitedByLava()
        }

    val Slate: DeferredBlock<Block> =
        registerBlockWithItem("slate", ::Block) {
            Properties.of()
                .mapColor(MapColor.STONE)
                .instrument(NoteBlockInstrument.SNARE)
                .strength(0.6f)
                .sound(SoundType.GRAVEL)
        }

    val DriedShortGrass: DeferredBlock<DriedShortGrass> =
        registerBlockWithItem("dried_short_grass", ::DriedShortGrass) {
            Properties.of()
                .mapColor(MapColor.PLANT)
                .replaceable()
                .noCollission()
                .instabreak()
                .sound(SoundType.GRASS)
                .offsetType(BlockBehaviour.OffsetType.XYZ)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
        }

    val DeadSeagrass: DeferredBlock<DeadSeagrassBlock> =
        registerBlockWithItem("dead_seagrass", ::DeadSeagrassBlock) {
            Properties.of()
                .mapColor(MapColor.WATER)
                .replaceable()
                .noCollission()
                .instabreak()
                .sound(SoundType.WET_GRASS)
                .offsetType(BlockBehaviour.OffsetType.XYZ)
                .pushReaction(PushReaction.DESTROY)
        }

    val TallDeadSeagrass: DeferredBlock<TallDeadSeagrassBlock> =
        registerBlock("tall_dead_seagrass", ::TallDeadSeagrassBlock) {
            Properties.of()
                .mapColor(MapColor.WATER)
                .replaceable()
                .noCollission()
                .instabreak()
                .sound(SoundType.WET_GRASS)
                .pushReaction(PushReaction.DESTROY)
        }

    private fun <B : Block> registerBlockWithItem(
        name: String, factory: Function<Properties, B>, properties: Supplier<Properties>
    ): DeferredBlock<B> {
        val block: DeferredBlock<B> = Registry.register(name, Supplier<B> { factory.apply(properties.get()) })
        ItemSetup.Registry.registerSimpleBlockItem(block)
        return block
    }

    private fun <B : Block> registerBlock(
        name: String, factory: Function<Properties, B>, properties: Supplier<Properties>
    ): DeferredBlock<B> {
        return Registry.register(name, Supplier<B> { factory.apply(properties.get()) })
    }

    fun init() {
        Registry.register(MOD_BUS)
    }

    private fun log(topMapColor: MapColor, sideMapColor: MapColor): Properties {
        return Properties.of().mapColor { state: BlockState ->
            if (state.getValue(RotatedPillarBlock.AXIS) === Direction.Axis.Y) topMapColor
            else sideMapColor
        }.instrument(NoteBlockInstrument.BASS).strength(2.0f).sound(SoundType.WOOD).ignitedByLava()
    }
}