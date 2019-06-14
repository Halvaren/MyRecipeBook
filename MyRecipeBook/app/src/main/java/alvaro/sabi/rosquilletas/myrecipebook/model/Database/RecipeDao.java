package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RecipeDao {

    @Insert
    long insertRecipe(Recipe recipe);

    @Insert
    long[] insertIngredients(Ingredient... ingredients);

    @Insert
    void insertSteps(StepToFollow... steps);

    @Insert
    void insertRecipeIngredients(RecipeIngredients... recipeIngredients);

    @Update
    void updateRecipe(Recipe recipe);

    @Query("SELECT count(*) FROM Recipes WHERE name = :recipeName")
    int checkRecipeExists(String recipeName);

    @Query("SELECT count(*) FROM RecipeIngredients WHERE recipeID = :recipeID")
    int nIngredientsOfRecipe(int recipeID);

    @Query("SELECT count(*) FROM StepsToFollow WHERE recipeID = :recipeID")
    int nStepsOfRecipe(int recipeID);

    @Query("SELECT * FROM Recipes")
    Recipe[] loadAllRecipes();

    @Query("SELECT * FROM Recipes WHERE typeID = :recipeType")
    Recipe[] loadAllRecipesOfType(int recipeType);

    @Query("SELECT * FROM RecipeIngredients WHERE recipeID = :recipeID")
    RecipeIngredients[] loadAllRecipeIngredientsFromRecipe(int recipeID);

    @Query("SELECT * FROM Ingredients WHERE id IN " +
            "(SELECT ingredientID FROM RecipeIngredients WHERE recipeID = :recipeID)")
    Ingredient[] loadAllIngredientsFromRecipe(int recipeID);

    @Query("SELECT * FROM StepsToFollow WHERE recipeID = :recipeID")
    StepToFollow[] loadAllStepToFollowFromRecipe(int recipeID);

    @Delete
    int deleteRecipe(Recipe recipe);

    @Delete
    int deleteIngredients(Ingredient... ingredients);

    @Delete
    int deleteSteps(StepToFollow... steps);

    @Delete
    int deleteRecipeIngredients(RecipeIngredients... recipeIngredients);

}
