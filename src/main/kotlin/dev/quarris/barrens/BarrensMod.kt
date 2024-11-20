package dev.quarris.barrens

import dev.quarris.barrens.setup.*
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig

@Mod(ModRef.ID)
object BarrensMod {

    init {
        ItemSetup.init()
        BlockSetup.init()
        BlockEntitySetup.init()
        FeatureSetup.init()
        CreativeTabSetup.init()

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BarrensConfigs.Spec)
    }

}