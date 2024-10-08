package dev.quarris.wastelands.setup

import dev.quarris.wastelands.ModRef
import net.minecraft.core.registries.Registries
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome


object TagSetup {

    object Blocks {
        val DEAD_OAK_LOGS = BlockTags.create(ModRef.res("dead_oak_logs"))
    }

    object Items {
        val DEAD_OAK_LOGS = ItemTags.create(ModRef.res("dead_oak_logs"))
    }

    object Biomes {
        val IsDead = create("is_dead")

        private fun create(name: String) : TagKey<Biome> {
            return TagKey.create(Registries.BIOME, ModRef.res(name))
        }
    }



}