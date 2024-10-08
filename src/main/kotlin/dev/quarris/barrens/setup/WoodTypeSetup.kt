package dev.quarris.barrens.setup

import net.minecraft.world.level.block.state.properties.WoodType

object WoodTypeSetup {

    val DeadOak = WoodType.register(WoodType("dead_oak", BlockSetSetup.DeadOak))

}