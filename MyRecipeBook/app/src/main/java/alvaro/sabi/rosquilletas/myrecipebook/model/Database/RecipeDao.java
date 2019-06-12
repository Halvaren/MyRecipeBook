package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RecipeDao {

    @Insert
    void insertRecipeTypeNames(RecipeType... recipeType);

    @Insert
    void insertRecipe(Recipe recipe);

    @Insert
    void insertIngredients(Ingredient... ingredients);

    @Insert
    void insertSteps(StepToFollow... steps);

    @Insert
    void insertRecipeIngredients(RecipeIngredients... recipeIngredients);

    @Update
    void updateRecipe(Recipe recipe);

    @Query("SELECT count(*) FROM Recipes WHERE name = :recipeName")
    int checkRecipeExists(String recipeName);

    @Query("SELECT name FROM RecipeType")
    String[] loadAllRecipeTypeNames();

    @Query("SELECT count(*) FROM RecipeType")
    int recipeTypeNamesNumberOfRows();

    @Query("SELECT * FROM Recipes")
    Recipe[] loadAllRecipes();

    @Query("SELECT * FROM Recipes WHERE typeID = :recipeType")
    Recipe[] loadAllRecipesOfType(int recipeType);

    @Query("SELECT * FROM RecipeIngredients WHERE recipeName = :recipeName")
    RecipeIngredients[] loadAllRecipeIngredientsFromRecipe(String recipeName);

    @Query("SELECT * FROM Ingredients WHERE name IN " +
            "(SELECT ingredientName FROM RecipeIngredients WHERE recipeName = :recipeName)")
    Ingredient[] loadAllIngredientsFromRecipe(String recipeName);

    @Query("SELECT * FROM StepsToFollow WHERE recipeName = :recipeName")
    StepToFollow[] loadAllStepToFollowFromRecipe(String recipeName);

    @Delete
    int deleteRecipe(Recipe recipe);

    @Delete
    int deleteIngredients(Ingredient... ingredients);

    @Delete
    int deleteSteps(StepToFollow... steps);

    @Delete
    int deleteRecipeIngredients(RecipeIngredients... recipeIngredients);

}
