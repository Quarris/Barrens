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

        this.add(BlockSetup.DEAD_OAK_LOG.get(), "Dead Oak Log")
        this.add(BlockSetup.STRIPPED_DEAD_OAK_LOG.get(), "Stripped Dead Oak Log")
        this.add(BlockSetup.CHARRED_DEAD_OAK_LOG.get(), "Charred Dead Oak Log")
        this.add(BlockSetup.STRIPPED_CHARRED_DEAD_OAK_LOG.get(), "Charred Stripped Dead Oak Log")

        this.add(BlockSetup.DEAD_OAK_WOOD.get(), "Dead Oak Wood")
        this.add(BlockSetup.STRIPPED_DEAD_OAK_WOOD.get(), "Stripped Dead Oak Wood")
        this.add(BlockSetup.CHARRED_DEAD_OAK_WOOD.get(), "Charred Dead Oak Wood")
        this.add(BlockSetup.STRIPPED_CHARRED_DEAD_OAK_WOOD.get(), "Charred Stripped Dead Oak Wood")

        this.add(BlockSetup.DEAD_OAK_PLANKS.get(), "Dead Oak Planks")
        this.add(BlockSetup.DEAD_OAK_SLAB.get(), "Dead Oak Slab")
        this.add(BlockSetup.DEAD_OAK_STAIRS.get(), "Dead Oak Stairs")
        this.add(BlockSetup.DEAD_OAK_FENCE.get(), "Dead Oak Fence")
        this.add(BlockSetup.DEAD_OAK_FENCE_GATE.get(), "Dead Oak Fence Gate")
        this.add(BlockSetup.DEAD_OAK_PRESSURE_PLATE.get(), "Dead Oak Pressure Plate")
        this.add(BlockSetup.DEAD_OAK_BUTTON.get(), "Dead Oak Button")
        this.add(BlockSetup.DEAD_OAK_DOOR.get(), "Dead Oak Door")
        this.add(BlockSetup.DEAD_OAK_TRAPDOOR.get(), "Dead Oak Trapdoor")
    }

    private fun addMisc() {
        this.add(ModRef.key("tab", "main"), "Wastelands")
        this.add(ModRef.key("generator", "wastelands"), "Wastelands")
    }
}