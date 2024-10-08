package dev.quarris.barrens

import dev.quarris.barrens.setup.BlockSetup
import dev.quarris.barrens.setup.CreativeTabSetup
import dev.quarris.barrens.setup.FeatureSetup
import dev.quarris.barrens.setup.ItemSetup
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig

@Mod(ModRef.ID)
object BarrensMod {

    init {
        ItemSetup.init()
        BlockSetup.init()
        FeatureSetup.init()
        CreativeTabSetup.init()

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BarrensConfigs.Spec)
    }

}