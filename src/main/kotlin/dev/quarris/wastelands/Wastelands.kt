package dev.quarris.wastelands

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.CreativeTabSetup
import dev.quarris.wastelands.setup.FeatureSetup
import dev.quarris.wastelands.setup.ItemSetup
import net.neoforged.fml.common.Mod

@Mod(ModRef.ID)
object Wastelands {

    init {
        ItemSetup.init()
        BlockSetup.init()
        FeatureSetup.init()
        CreativeTabSetup.init()
    }

}