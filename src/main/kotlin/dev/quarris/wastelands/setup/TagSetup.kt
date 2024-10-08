package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.biome.Biome


object TagSetup {

    object Blocks {
        val DeadOakLogs = BlockTags.create(ModRef.res("dead_oak_logs"))
    }

    object Items {
        val DeadOakLogs = ItemTags.create(ModRef.res("dead_oak_logs"))

        val RawMaterialsNuggets = createCommon("raw_materials/nuggets")
        val RawMaterialsNuggetsIron = createCommon("raw_materials/nuggets/iron")
        val RawMaterialsNuggetsGold = createCommon("raw_materials/nuggets/gold")
        val RawMaterialsNuggetsCopper = createCommon("raw_materials/nuggets/copper")

        private fun createCommon(name: String): TagKey<Item> {
            return ItemTags.create(ResourceLocation("c", name))
        }
    }

    object Biomes {
        val IsDead = create("is_dead")

        private fun create(name: String) : TagKey<Biome> {
            return TagKey.create(Registries.BIOME, ModRef.res(name))
        }
    }



}