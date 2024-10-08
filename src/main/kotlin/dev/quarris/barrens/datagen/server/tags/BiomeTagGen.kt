package dev.quarris.barrens.datagen.server.tags

import dev.quarris.barrens.ModRef
import dev.quarris.barrens.setup.BiomeSetup
import dev.quarris.barrens.setup.TagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.BiomeTagsProvider
import net.minecraft.tags.BiomeTags
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class BiomeTagGen(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper
) : BiomeTagsProvider (output, lookupProvider, ModRef.ID, existingFileHelper) {
    override fun addTags(provider: HolderLookup.Provider) {
        arrayOf(
            BiomeTags.IS_OVERWORLD,
            Tags.Biomes.IS_DEAD,
            Tags.Biomes.IS_WASTELAND,
            Tags.Biomes.IS_HOT_OVERWORLD,
            Tags.Biomes.IS_DRY_OVERWORLD,
            TagSetup.Biomes.IsDead
        ).forEach { tag ->
            this.tag(tag)
                .addOptional(BiomeSetup.Wasteland.location())
                .addOptional(BiomeSetup.WastelandShore.location())
                .addOptional(BiomeSetup.DeadOcean.location())
        }
    }
}