package dev.quarris.barrens.setup

import dev.quarris.barrens.ModRef
import dev.quarris.barrens.block.entity.BHangingSignBlockEntity
import dev.quarris.barrens.block.entity.BSignBlockEntity
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object BlockEntitySetup {
    val Registry: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ModRef.ID)

    val Sign = Registry.register("sign") { BlockEntityType.Builder.of(::BSignBlockEntity, BlockSetup.DeadOakSign.get(), BlockSetup.DeadOakWallSign.get()).build(null) }
    val HangingSign = Registry.register("hanging_sign") { BlockEntityType.Builder.of(::BHangingSignBlockEntity, BlockSetup.DeadOakHangingSign.get(), BlockSetup.DeadOakWallHangingSign.get()).build(null) }

    fun init() {
        Registry.register(MOD_BUS)
    }
}