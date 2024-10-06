package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.ItemTagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.ShapedRecipePattern
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.Tags
import java.util.concurrent.CompletableFuture

class RecipesGen(output: PackOutput, registries: CompletableFuture<HolderLookup.Provider>) :
    RecipeProvider(output, registries) {

    override fun buildRecipes(output: RecipeOutput) {
        createWoodRecipes(output)

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.FLINT, 4)
            .requires(BlockSetup.SLATE)
            .unlockedBy("has_slate", has(BlockSetup.SLATE))
            .save(output)

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockSetup.SLATE)
            .pattern("##")
            .pattern("##")
            .define('#', Items.FLINT)
            .unlockedBy("has_flint", has(Items.FLINT))
            .save(output)
    }

    private fun createWoodRecipes(output: RecipeOutput) {
        planksFromLog(output, BlockSetup.DEAD_OAK_PLANKS, ItemTagSetup.DEAD_OAK_LOGS, 2)
        woodFromLogs(output, BlockSetup.DEAD_OAK_WOOD, BlockSetup.DEAD_OAK_LOG)
        woodFromLogs(output, BlockSetup.CHARRED_DEAD_OAK_WOOD, BlockSetup.CHARRED_DEAD_OAK_LOG)
        woodFromLogs(output, BlockSetup.STRIPPED_DEAD_OAK_WOOD, BlockSetup.STRIPPED_DEAD_OAK_LOG)
        woodFromLogs(output, BlockSetup.STRIPPED_CHARRED_DEAD_OAK_WOOD, BlockSetup.STRIPPED_CHARRED_DEAD_OAK_LOG)
        woodFromLogs(output, BlockSetup.DEAD_OAK_LOG, BlockSetup.DEAD_OAK_WOOD)
        woodFromLogs(output, BlockSetup.CHARRED_DEAD_OAK_LOG, BlockSetup.CHARRED_DEAD_OAK_WOOD)
        woodFromLogs(output, BlockSetup.STRIPPED_DEAD_OAK_LOG, BlockSetup.STRIPPED_DEAD_OAK_WOOD)
        woodFromLogs(output, BlockSetup.STRIPPED_CHARRED_DEAD_OAK_LOG, BlockSetup.STRIPPED_CHARRED_DEAD_OAK_WOOD)
        slab(output, RecipeCategory.BUILDING_BLOCKS, BlockSetup.DEAD_OAK_SLAB, BlockSetup.DEAD_OAK_PLANKS)
        stairs(output, BlockSetup.DEAD_OAK_STAIRS, BlockSetup.DEAD_OAK_PLANKS)
        fence(output, BlockSetup.DEAD_OAK_FENCE, BlockSetup.DEAD_OAK_PLANKS)
        fenceGate(output, BlockSetup.DEAD_OAK_FENCE_GATE, BlockSetup.DEAD_OAK_PLANKS)
        pressurePlate(output, BlockSetup.DEAD_OAK_PRESSURE_PLATE, BlockSetup.DEAD_OAK_PLANKS)
        button(output, BlockSetup.DEAD_OAK_BUTTON, BlockSetup.DEAD_OAK_PLANKS)
        door(output, BlockSetup.DEAD_OAK_DOOR, BlockSetup.DEAD_OAK_PLANKS)
        trapdoor(output, BlockSetup.DEAD_OAK_TRAPDOOR, BlockSetup.DEAD_OAK_PLANKS)
    }

    private fun stairs(recipeOutput: RecipeOutput, outputItem: ItemLike, material: ItemLike) {
        stairBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun fence(recipeOutput: RecipeOutput, outputItem: ItemLike, material: ItemLike) {
        fenceBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun fenceGate(recipeOutput: RecipeOutput, outputItem: ItemLike, material: ItemLike) {
        fenceGateBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun button(recipeOutput: RecipeOutput, outputItem: ItemLike, material: ItemLike) {
         buttonBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun door(recipeOutput: RecipeOutput, outputItem: ItemLike, material: ItemLike) {
        doorBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun trapdoor(recipeOutput: RecipeOutput, outputItem: ItemLike, material: ItemLike) {
        trapdoorBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }
}