package dev.quarris.wastelands

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.CreativeTabSetup
import dev.quarris.wastelands.setup.FeatureSetup
import dev.quarris.wastelands.setup.ItemSetup
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.config.ModConfigs

@Mod(ModRef.ID)
class Wastelands(
    modContainer: ModContainer
) {

    init {
        ItemSetup.init()
        BlockSetup.init()
        FeatureSetup.init()
        CreativeTabSetup.init()

        modContainer.registerConfig(ModConfig.Type.CLIENT, WastelandsConfigs.SPEC)
    }

}