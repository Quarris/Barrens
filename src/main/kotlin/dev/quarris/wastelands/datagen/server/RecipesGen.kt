package dev.quarris.wastelands.datagen.server

import dev.quarris.wastelands.setup.BlockSetup
import dev.quarris.wastelands.setup.ItemSetup
import dev.quarris.wastelands.setup.TagSetup
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
import net.minecraft.data.recipes.packs.VanillaRecipeProvider
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import java.util.concurrent.CompletableFuture

class RecipesGen(output: PackOutput, registries: CompletableFuture<HolderLookup.Provider>) :
    RecipeProvider(output, registries) {

    override fun buildRecipes(output: RecipeOutput) {
        createWoodRecipes(output)
        createRawNuggetRecipes(output)

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

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockSetup.DriedSandstone)
            .pattern("##")
            .pattern("##")
            .define('#', BlockSetup.DriedSand)
            .unlockedBy("has_sand", has(BlockSetup.DriedSand))
            .save(output)
    }

    private fun createRawNuggetRecipes(output: RecipeOutput) {
        storageBlock(output, Items.RAW_IRON, ItemSetup.RawIronNugget)
        storageBlock(output, Items.RAW_GOLD, ItemSetup.RawGoldNugget)
        storageBlock(output, Items.RAW_COPPER, ItemSetup.RawCopperNugget)
        unpackStorageBlock(output, ItemSetup.RawIronNugget, Items.RAW_IRON)
        unpackStorageBlock(output, ItemSetup.RawGoldNugget, Items.RAW_GOLD)
        unpackStorageBlock(output, ItemSetup.RawCopperNugget, Items.RAW_COPPER)


        oreSmelting(output, listOf(ItemSetup.RawIronNugget), RecipeCategory.MISC, Items.IRON_NUGGET, 0.077f, 24, "iron_nugget")
        oreBlasting(output, listOf(ItemSetup.RawIronNugget), RecipeCategory.MISC, Items.IRON_NUGGET, 0.077f, 12, "iron_nugget")
        oreSmelting(output, listOf(ItemSetup.RawGoldNugget), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.11f, 24, "gold_nugget")
        oreBlasting(output, listOf(ItemSetup.RawGoldNugget), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.11f, 12, "gold_nugget")
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

    private fun storageBlock(output: RecipeOutput, storageItem: ItemLike, material: ItemLike) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, storageItem)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .define('#', material)
            .unlockedBy("has_material", has(material))
            .save(output)
    }

    private fun unpackStorageBlock(output: RecipeOutput, material: ItemLike, storageItem: ItemLike) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, material, 9)
            .requires(storageItem)
            .unlockedBy("has_storage_item", has(storageItem))
            .save(output)
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