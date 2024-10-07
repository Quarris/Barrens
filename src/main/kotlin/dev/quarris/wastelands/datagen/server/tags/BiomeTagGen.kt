package dev.quarris.wastelands.datagen.server.tags

import dev.quarris.wastelands.ModRef
import dev.quarris.wastelands.setup.BiomeSetup
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.BiomeTagsProvider
import net.minecraft.resources.ResourceKey
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
        this.tag(BiomeTags.IS_OVERWORLD).addOptional(BiomeSetup.WASTELANDS.location())
    }
}