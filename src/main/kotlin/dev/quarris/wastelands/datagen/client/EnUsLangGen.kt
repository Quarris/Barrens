package dev.quarris.wastelands.datagen.client

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.ItemSetup
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider

class EnUsLangGen(output: PackOutput) : LanguageProvider(output, ModRef.ID, "en_us") {
    override fun addTranslations() {
        addBlocks()
        addItems()
        addMisc()
    }

    private fun addItems() {
        add(ItemSetup.RawIronNugget.get(), "Raw Iron Nugget")
        add(ItemSetup.RawGoldNugget.get(), "Raw Gold Nugget")
        add(ItemSetup.RawCopperNugget.get(), "Raw Copper Nugget")
    }

    private fun addBlocks() {
        add(BlockSetup.DriedDirt.get(), "Dried Dirt")
        add(BlockSetup.DriedSand.get(), "Dried Sand")
        add(BlockSetup.DriedSandstone.get(), "Dried Sandstone")
        add(BlockSetup.PorousStone.get(), "Porous Stone")

        add(BlockSetup.DeadOakLog.get(), "Dead Oak Log")
        add(BlockSetup.StrippedDeakOakLog.get(), "Stripped Dead Oak Log")
        add(BlockSetup.CharredDeadOakLog.get(), "Charred Dead Oak Log")
        add(BlockSetup.StrippedCharredDeadOakLog.get(), "Charred Stripped Dead Oak Log")

        add(BlockSetup.DeadOakWood.get(), "Dead Oak Wood")
        add(BlockSetup.StrippedDeadOakWood.get(), "Stripped Dead Oak Wood")
        add(BlockSetup.CharredDeadOakWood.get(), "Charred Dead Oak Wood")
        add(BlockSetup.StrippedCharredDeadOakWood.get(), "Charred Stripped Dead Oak Wood")

        add(BlockSetup.DeadOakPlanks.get(), "Dead Oak Planks")
        add(BlockSetup.DeadOakSlab.get(), "Dead Oak Slab")
        add(BlockSetup.DeadOakStairs.get(), "Dead Oak Stairs")
        add(BlockSetup.DeadOakFence.get(), "Dead Oak Fence")
        add(BlockSetup.DeadOakFenceGate.get(), "Dead Oak Fence Gate")
        add(BlockSetup.DeadOakPressurePlate.get(), "Dead Oak Pressure Plate")
        add(BlockSetup.DeadOakButton.get(), "Dead Oak Button")
        add(BlockSetup.DeadOakDoor.get(), "Dead Oak Door")
        add(BlockSetup.DeadOakTrapdoor.get(), "Dead Oak Trapdoor")

        add(BlockSetup.Slate.get(), "Slate")

        add(BlockSetup.DriedShortGrass.get(), "Short Dried Grass")
        add(BlockSetup.DeadSeagrass.get(), "Dead Seagrass")
        add(BlockSetup.TallDeadSeagrass.get(), "Tall Dead Seagrass")
    }

    private fun addMisc() {
        add(ModRef.key("tab", "main"), "Wastelands")
        add(ModRef.key("generator", "wastelands"), "Wastelands")
    }
}