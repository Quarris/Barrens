package dev.quarris.barrens.setup

import dev.quarris.barrens.ModRef
import dev.quarris.barrens.block.*
import net.minecraft.core.Direction
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import java.util.function.Function
import java.util.function.Supplier

object BlockSetup {

    val Registry: DeferredRegister<Block> = DeferredRegister.create(Registries.BLOCK, ModRef.ID)

    val DriedDirt: RegistryObject<DriedDirtBlock> = registerBlockWithItem(
        "dried_dirt", ::DriedDirtBlock
    ) {
        Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.2F, 2.0F)
    }

    val DriedSand: RegistryObject<Block> = registerBlockWithItem(
        "dried_sand", { props ->
            SandBlock(
                0x5D5941,
                props
            )
        }
    ) {
        Properties.of().mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.SNARE)
            .sound(SoundType.SAND)
            .strength(0.6F)
    }

    val DriedSandstone: RegistryObject<Block> = registerBlockWithItem(
        "dried_sandstone", ::DriedDirtBlock
    ) {
        Properties.of().mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.BASS)
            .requiresCorrectToolForDrops()
            .strength(0.8F)
    }

    val PorousStone: RegistryObject<PorousStoneBlock> = registerBlockWithItem(
        "porous_stone", ::PorousStoneBlock
    ) {
        Properties.of().mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.2F)
    }

    val AncientOakSapling: RegistryObject<SaplingBlock> =
        registerBlockWithItem("ancient_oak_sapling", { props ->
            SaplingBlock(TreeGrowerSetup.DeadOak, props)
        }) {
            Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)
        }

    val DeadOakLog: RegistryObject<RotatedPillarBlock> =
        registerBlockWithItem("dead_oak_log", { props -> WoodBlock(props, StrippedDeakOakLog) }) {
            log(
                MapColor.COLOR_LIGHT_GRAY, MapColor.COLOR_GRAY
            )
        }

    val DeadOakWood: RegistryObject<RotatedPillarBlock> = registerBlockWithItem(
        "dead_oak_wood",
        { props -> WoodBlock(props, StrippedDeadOakWood) }) { Properties.copy(DeadOakLog.get()) }

    val StrippedDeakOakLog: RegistryObject<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_dead_oak_log", ::RotatedPillarBlock
    ) { Properties.copy(DeadOakLog.get()) }

    val StrippedDeadOakWood: RegistryObject<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_dead_oak_wood", ::RotatedPillarBlock
    ) { Properties.copy(DeadOakLog.get()) }

    val CharredDeadOakLog: RegistryObject<RotatedPillarBlock> =
        registerBlockWithItem("charred_dead_oak_log", { props -> WoodBlock(props, StrippedCharredDeadOakLog) }) {
            Properties.copy(
                DeadOakLog.get()
            )
        }

    val CharredDeadOakWood: RegistryObject<RotatedPillarBlock> =
        registerBlockWithItem("charred_dead_oak_wood", { props -> WoodBlock(props, StrippedCharredDeadOakWood) }) {
            Properties.copy(
                DeadOakLog.get()
            )
        }

    val StrippedCharredDeadOakLog: RegistryObject<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_charred_dead_oak_log", ::RotatedPillarBlock
    ) { Properties.copy(DeadOakLog.get()) }

    val StrippedCharredDeadOakWood: RegistryObject<RotatedPillarBlock> = registerBlockWithItem(
        "stripped_charred_dead_oak_wood", ::RotatedPillarBlock
    ) { Properties.copy(DeadOakLog.get()) }

    val DeadOakPlanks: RegistryObject<Block> = registerBlockWithItem(
        "dead_oak_planks", ::Block
    ) {
        Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASS).strength(1.0F)
            .sound(SoundType.WOOD).ignitedByLava()
    }

    val DeadOakSlab: RegistryObject<SlabBlock> =
        registerBlockWithItem("dead_oak_slab", ::SlabBlock) { Properties.copy(DeadOakPlanks.get()) }

    val DeadOakStairs: RegistryObject<StairBlock> = registerBlockWithItem("dead_oak_stairs",
        { props -> StairBlock(DeadOakPlanks.get().defaultBlockState(), props) },
        { Properties.copy(DeadOakPlanks.get()) })
    val DeadOakFence: RegistryObject<FenceBlock> = registerBlockWithItem(
        "dead_oak_fence", ::FenceBlock
    ) {
        Properties.of().mapColor(DeadOakPlanks.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS)
            .strength(2.0f, 3.0f).ignitedByLava().sound(SoundType.WOOD)
    }
    val DeadOakFenceGate: RegistryObject<FenceGateBlock> =
        registerBlockWithItem("dead_oak_fence_gate", { props -> FenceGateBlock(props, WoodTypeSetup.DeadOak) }, {
            Properties.of().mapColor(DeadOakPlanks.get().defaultMapColor()).forceSolidOn()
                .instrument(NoteBlockInstrument.BASS).strength(1.0F).ignitedByLava()
        })
    val DeadOakPressurePlate: RegistryObject<PressurePlateBlock> = registerBlockWithItem("dead_oak_pressure_plate",
        { props -> PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, props, BlockSetSetup.DeadOak) },
        {
            Properties.of().mapColor(DeadOakPlanks.get().defaultMapColor()).forceSolidOn()
                .instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5f).ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
        })
    val DeadOakButton: RegistryObject<ButtonBlock> =
        registerBlockWithItem("dead_oak_button", { props -> ButtonBlock(props, BlockSetSetup.DeadOak, 30, true) }, {
            Properties.of().noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY)
        })

    val DeadOakDoor: RegistryObject<DoorBlock> =
        registerBlockWithItem("dead_oak_door", { props -> DoorBlock(props, BlockSetSetup.DeadOak) }) {
            Properties.of()
                .mapColor(DeadOakPlanks.get().defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .noOcclusion()
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
        }

    val DeadOakTrapdoor: RegistryObject<TrapDoorBlock> =
        registerBlockWithItem("dead_oak_trapdoor", { props -> TrapDoorBlock(props, BlockSetSetup.DeadOak) }) {
            Properties.of()
                .mapColor(DeadOakPlanks.get().defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0f)
                .noOcclusion()
                .isValidSpawn { _, _, _, _ -> false }
                .ignitedByLava()
        }

    val Slate: RegistryObject<Block> =
        registerBlockWithItem("slate", ::Block) {
            Properties.of()
                .mapColor(MapColor.STONE)
                .instrument(NoteBlockInstrument.SNARE)
                .strength(0.6f)
                .sound(SoundType.GRAVEL)
        }

    val DriedShortGrass: RegistryObject<DriedShortGrass> =
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

    val DeadSeagrass: RegistryObject<DeadSeagrassBlock> =
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

    val TallDeadSeagrass: RegistryObject<TallDeadSeagrassBlock> =
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
    ): RegistryObject<B> {
        val block: RegistryObject<B> = Registry.register(name) { factory.apply(properties.get()) }
        ItemSetup.registerBlockItem(block)
        return block
    }

    private fun <B : Block> registerBlock(
        name: String, factory: Function<Properties, B>, properties: Supplier<Properties>
    ): RegistryObject<B> {
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