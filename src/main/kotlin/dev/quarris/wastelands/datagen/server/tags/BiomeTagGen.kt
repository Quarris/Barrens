package dev.quarris.wastelands.datagen.server.tags

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BiomeSetup
import dev.quarris.wastelands.setup.TagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.BiomeTagsProvider
import net.minecraft.tags.BiomeTags
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.ExistingFileHelper
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