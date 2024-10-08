package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.TagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import java.util.concurrent.CompletableFuture

class RecipesGen(output: PackOutput, registries: CompletableFuture<HolderLookup.Provider>) :
    RecipeProvider(output, registries) {

    override fun buildRecipes(output: RecipeOutput) {
        createWoodRecipes(output)

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.FLINT, 4)
            .requires(BlockSetup.Slate)
            .unlockedBy("has_slate", has(BlockSetup.Slate))
            .save(output)

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockSetup.Slate)
            .pattern("##")
            .pattern("##")
            .define('#', Items.FLINT)
            .unlockedBy("has_flint", has(Items.FLINT))
            .save(output)
    }

    private fun createWoodRecipes(output: RecipeOutput) {
        planksFromLog(output, BlockSetup.DeadOakPlanks, TagSetup.Items.DeadOakLogs, 2)
        woodFromLogs(output, BlockSetup.DeadOakWood, BlockSetup.DeadOakLog)
        woodFromLogs(output, BlockSetup.CharredDeadOakWood, BlockSetup.CharredDeadOakLog)
        woodFromLogs(output, BlockSetup.StrippedDeadOakWood, BlockSetup.StrippedDeakOakLog)
        woodFromLogs(output, BlockSetup.StrippedCharredDeadOakWood, BlockSetup.StrippedCharredDeadOakLog)
        woodFromLogs(output, BlockSetup.DeadOakLog, BlockSetup.DeadOakWood)
        woodFromLogs(output, BlockSetup.CharredDeadOakLog, BlockSetup.CharredDeadOakWood)
        woodFromLogs(output, BlockSetup.StrippedDeakOakLog, BlockSetup.StrippedDeadOakWood)
        woodFromLogs(output, BlockSetup.StrippedCharredDeadOakLog, BlockSetup.StrippedCharredDeadOakWood)
        slab(output, RecipeCategory.BUILDING_BLOCKS, BlockSetup.DeadOakSlab, BlockSetup.DeadOakPlanks)
        stairs(output, BlockSetup.DeadOakStairs, BlockSetup.DeadOakPlanks)
        fence(output, BlockSetup.DeadOakFence, BlockSetup.DeadOakPlanks)
        fenceGate(output, BlockSetup.DeadOakFenceGate, BlockSetup.DeadOakPlanks)
        pressurePlate(output, BlockSetup.DeadOakPressurePlate, BlockSetup.DeadOakPlanks)
        button(output, BlockSetup.DeadOakButton, BlockSetup.DeadOakPlanks)
        door(output, BlockSetup.DeadOakDoor, BlockSetup.DeadOakPlanks)
        trapdoor(output, BlockSetup.DeadOakTrapdoor, BlockSetup.DeadOakPlanks)
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