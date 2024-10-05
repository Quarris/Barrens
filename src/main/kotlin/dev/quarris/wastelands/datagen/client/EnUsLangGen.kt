package dev.quarris.wastelands.datagen.client

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BlockSetup
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider

class EnUsLangGen(output: PackOutput) : LanguageProvider(output, ModRef.ID, "en_us") {
    override fun addTranslations() {
        this.addBlocks()
        this.addMisc()
    }

    private fun addBlocks() {
        this.add(BlockSetup.DRIED_DIRT.get(), "Dried Dirt")
        this.add(BlockSetup.DEAD_WOOD_LOG.get(), "Dead Wood Log")
        this.add(BlockSetup.STRIPPED_DEAD_WOOD_LOG.get(), "Stripped Dead Wood Log")
        this.add(BlockSetup.CHARRED_DEAD_WOOD_LOG.get(), "Charred Dead Wood Log")
        this.add(BlockSetup.STRIPPED_CHARRED_DEAD_WOOD_LOG.get(), "Charred Stripped Dead Wood Log")
    }

    private fun addMisc() {
        this.add("generator.wastelands.wastelands", "Wastelands")
    }
}