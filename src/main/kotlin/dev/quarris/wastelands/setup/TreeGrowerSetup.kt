package dev.quarris.wastelands.setup

import net.minecraft.world.level.block.grower.TreeGrower
import java.util.*

object TreeGrowerSetup {

    val DeadOak = TreeGrower("dead_oak", Optional.empty(), Optional.of(ConfiguredFeatureSetup.DeadOakTree), Optional.empty())

}