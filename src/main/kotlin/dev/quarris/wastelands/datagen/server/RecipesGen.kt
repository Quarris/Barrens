package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.ItemSetup
import dev.quarris.wastelands.setup.TagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class RecipesGen(output: PackOutput) :
    RecipeProvider(output) {

    override fun buildRecipes(output: Consumer<FinishedRecipe>) {
        createWoodRecipes(output)
        createRawNuggetRecipes(output)

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.FLINT, 4)
            .requires(BlockSetup.Slate.get())
            .unlockedBy("has_slate", has(BlockSetup.Slate.get()))
            .save(output)

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockSetup.Slate.get())
            .pattern("##")
            .pattern("##")
            .define('#', Items.FLINT)
            .unlockedBy("has_flint", has(Items.FLINT))
            .save(output)

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockSetup.DriedSandstone.get())
            .pattern("##")
            .pattern("##")
            .define('#', BlockSetup.DriedSand.get())
            .unlockedBy("has_sand", has(BlockSetup.DriedSand.get()))
            .save(output)
    }

    private fun createRawNuggetRecipes(output: Consumer<FinishedRecipe>) {
        storageBlock(output, Items.RAW_IRON, ItemSetup.RawIronNugget.get())
        storageBlock(output, Items.RAW_GOLD, ItemSetup.RawGoldNugget.get())
        storageBlock(output, Items.RAW_COPPER, ItemSetup.RawCopperNugget.get())
        unpackStorageBlock(output, ItemSetup.RawIronNugget.get(), Items.RAW_IRON)
        unpackStorageBlock(output, ItemSetup.RawGoldNugget.get(), Items.RAW_GOLD)
        unpackStorageBlock(output, ItemSetup.RawCopperNugget.get(), Items.RAW_COPPER)


        oreSmelting(output, listOf(ItemSetup.RawIronNugget.get()), RecipeCategory.MISC, Items.IRON_NUGGET, 0.077f, 24, "iron_nugget")
        oreBlasting(output, listOf(ItemSetup.RawIronNugget.get()), RecipeCategory.MISC, Items.IRON_NUGGET, 0.077f, 12, "iron_nugget")
        oreSmelting(output, listOf(ItemSetup.RawGoldNugget.get()), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.11f, 24, "gold_nugget")
        oreBlasting(output, listOf(ItemSetup.RawGoldNugget.get()), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.11f, 12, "gold_nugget")
    }

    private fun createWoodRecipes(output: Consumer<FinishedRecipe>) {
        planksFromLog(output, BlockSetup.DeadOakPlanks.get(), TagSetup.Items.DeadOakLogs, 2)
        woodFromLogs(output, BlockSetup.DeadOakWood.get(), BlockSetup.DeadOakLog.get())
        woodFromLogs(output, BlockSetup.CharredDeadOakWood.get(), BlockSetup.CharredDeadOakLog.get())
        woodFromLogs(output, BlockSetup.StrippedDeadOakWood.get(), BlockSetup.StrippedDeakOakLog.get())
        woodFromLogs(output, BlockSetup.StrippedCharredDeadOakWood.get(), BlockSetup.StrippedCharredDeadOakLog.get())
        woodFromLogs(output, BlockSetup.DeadOakLog.get(), BlockSetup.DeadOakWood.get())
        woodFromLogs(output, BlockSetup.CharredDeadOakLog.get(), BlockSetup.CharredDeadOakWood.get())
        woodFromLogs(output, BlockSetup.StrippedDeakOakLog.get(), BlockSetup.StrippedDeadOakWood.get())
        woodFromLogs(output, BlockSetup.StrippedCharredDeadOakLog.get(), BlockSetup.StrippedCharredDeadOakWood.get())
        slab(output, RecipeCategory.BUILDING_BLOCKS, BlockSetup.DeadOakSlab.get(), BlockSetup.DeadOakPlanks.get())
        stairs(output, BlockSetup.DeadOakStairs.get(), BlockSetup.DeadOakPlanks.get())
        fence(output, BlockSetup.DeadOakFence.get(), BlockSetup.DeadOakPlanks.get())
        fenceGate(output, BlockSetup.DeadOakFenceGate.get(), BlockSetup.DeadOakPlanks.get())
        pressurePlate(output, BlockSetup.DeadOakPressurePlate.get(), BlockSetup.DeadOakPlanks.get())
        button(output, BlockSetup.DeadOakButton.get(), BlockSetup.DeadOakPlanks.get())
        door(output, BlockSetup.DeadOakDoor.get(), BlockSetup.DeadOakPlanks.get())
        trapdoor(output, BlockSetup.DeadOakTrapdoor.get(), BlockSetup.DeadOakPlanks.get())
    }

    private fun storageBlock(output: Consumer<FinishedRecipe>, storageItem: ItemLike, material: ItemLike) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, storageItem)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .define('#', material)
            .unlockedBy("has_material", has(material))
            .save(output)
    }

    private fun unpackStorageBlock(output: Consumer<FinishedRecipe>, material: ItemLike, storageItem: ItemLike) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, material, 9)
            .requires(storageItem)
            .unlockedBy("has_storage_item", has(storageItem))
            .save(output)
    }

    private fun stairs(recipeOutput: Consumer<FinishedRecipe>, outputItem: ItemLike, material: ItemLike) {
        stairBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun fence(recipeOutput: Consumer<FinishedRecipe>, outputItem: ItemLike, material: ItemLike) {
        fenceBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun fenceGate(recipeOutput: Consumer<FinishedRecipe>, outputItem: ItemLike, material: ItemLike) {
        fenceGateBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun button(recipeOutput: Consumer<FinishedRecipe>, outputItem: ItemLike, material: ItemLike) {
         buttonBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun door(recipeOutput: Consumer<FinishedRecipe>, outputItem: ItemLike, material: ItemLike) {
        doorBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }

    private fun trapdoor(recipeOutput: Consumer<FinishedRecipe>, outputItem: ItemLike, material: ItemLike) {
        trapdoorBuilder(outputItem, Ingredient.of(material)).unlockedBy(getHasName(material), has(material))
            .save(recipeOutput)
    }
}