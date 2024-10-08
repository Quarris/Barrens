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
        this.add(BlockSetup.DriedDirt.get(), "Dried Dirt")

        this.add(BlockSetup.DeadOakLog.get(), "Dead Oak Log")
        this.add(BlockSetup.StrippedDeakOakLog.get(), "Stripped Dead Oak Log")
        this.add(BlockSetup.CharredDeadOakLog.get(), "Charred Dead Oak Log")
        this.add(BlockSetup.StrippedCharredDeadOakLog.get(), "Charred Stripped Dead Oak Log")

        this.add(BlockSetup.DeadOakWood.get(), "Dead Oak Wood")
        this.add(BlockSetup.StrippedDeadOakWood.get(), "Stripped Dead Oak Wood")
        this.add(BlockSetup.CharredDeadOakWood.get(), "Charred Dead Oak Wood")
        this.add(BlockSetup.StrippedCharredDeadOakWood.get(), "Charred Stripped Dead Oak Wood")

        this.add(BlockSetup.DeadOakPlanks.get(), "Dead Oak Planks")
        this.add(BlockSetup.DeadOakSlab.get(), "Dead Oak Slab")
        this.add(BlockSetup.DeadOakStairs.get(), "Dead Oak Stairs")
        this.add(BlockSetup.DeadOakFence.get(), "Dead Oak Fence")
        this.add(BlockSetup.DeadOakFenceGate.get(), "Dead Oak Fence Gate")
        this.add(BlockSetup.DeadOakPressurePlate.get(), "Dead Oak Pressure Plate")
        this.add(BlockSetup.DeadOakButton.get(), "Dead Oak Button")
        this.add(BlockSetup.DeadOakDoor.get(), "Dead Oak Door")
        this.add(BlockSetup.DeadOakTrapdoor.get(), "Dead Oak Trapdoor")

        this.add(BlockSetup.Slate.get(), "Slate")

        this.add(BlockSetup.DriedShortGrass.get(), "Short Dried Grass")
        this.add(BlockSetup.DeadSeagrass.get(), "Dead Seagrass")
        this.add(BlockSetup.TallDeadSeagrass.get(), "Tall Dead Seagrass")
    }

    private fun addMisc() {
        this.add(ModRef.key("tab", "main"), "Wastelands")
        this.add(ModRef.key("generator", "wastelands"), "Wastelands")
    }
}