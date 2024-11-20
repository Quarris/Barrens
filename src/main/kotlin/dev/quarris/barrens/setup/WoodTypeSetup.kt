package dev.quarris.barrens.setup

import dev.quarris.barrens.ModRef
import net.minecraft.world.level.block.state.properties.WoodType

object WoodTypeSetup {

    val DeadOak = WoodType.register(WoodType(ModRef.res("dead_oak").toString(), BlockSetSetup.DeadOak))

}