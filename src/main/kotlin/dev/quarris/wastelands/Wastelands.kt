package dev.quarris.wastelands

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.CreativeTabSetup
import dev.quarris.wastelands.setup.FeatureSetup
import dev.quarris.wastelands.setup.ItemSetup
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig

@Mod(ModRef.ID)
object Wastelands {

    init {
        ItemSetup.init()
        BlockSetup.init()
        FeatureSetup.init()
        CreativeTabSetup.init()

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, WastelandsConfigs.Spec)
    }

}