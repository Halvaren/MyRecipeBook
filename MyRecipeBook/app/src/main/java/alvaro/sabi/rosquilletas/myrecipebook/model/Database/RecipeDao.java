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
    long insertIngredient(Ingredient ingredient);

    @Insert
    long[] insertIngredients(Ingredient... ingredients);

    @Insert
    void insertStep(StepToFollow stepToFollow);

    @Insert
    void insertSteps(StepToFollow... steps);

    @Insert
    void insertRecipeIngredient(RecipeIngredients recipeIngredient);

    @Insert
    void insertRecipeIngredients(RecipeIngredients... recipeIngredients);

    @Query("UPDATE Recipes " +
            "SET name = :recipeName, typeID = :recipeTypeID, guests = :recipeGuests, valuation = :recipeValuation, difficultyID = :recipeDifficultyID " +
            "WHERE id = :recipeID")
    void updateRecipe(int recipeID, String recipeName, int recipeTypeID, int recipeGuests, float recipeValuation, int recipeDifficultyID);

    @Query("UPDATE Ingredients " +
            "SET name = :ingredientName " +
            "WHERE id = :ingredientID")
    void updateIngredient(int ingredientID, String ingredientName);

    @Query("UPDATE StepsToFollow " +
            "SET description = :stepName " +
            "WHERE recipeID = :recipeID AND stepNum = :stepNum")
    void updateStep(int recipeID, int stepNum, String stepName);

    @Query("UPDATE RecipeIngredients " +
            "SET ingredientID = :ingredientID " +
            "WHERE recipeID = :recipeID")
    void updateRecipeIngredient(int recipeID, int ingredientID);

    @Query("SELECT MAX(id) FROM Recipes")
    int lastID();

    @Query("SELECT count(*) FROM Ingredients WHERE name = :ingredientName")
    int checkIngredientExists(String ingredientName);

    @Query("SELECT count(*) FROM RecipeIngredients WHERE recipeID = :recipeID")
    int nIngredientsOfRecipe(int recipeID);

    @Query("SELECT count(*) FROM StepsToFollow WHERE recipeID = :recipeID")
    int nStepsOfRecipe(int recipeID);

    @Query("SELECT * FROM Recipes")
    Recipe[] loadAllRecipes();

    @Query("SELECT * FROM Recipes WHERE id = :recipeID")
    Recipe loadRecipeByID(int recipeID);

    @Query("SELECT * FROM Recipes WHERE typeID = :recipeType")
    Recipe[] loadAllRecipesOfType(int recipeType);

    @Query("SELECT * FROM RecipeIngredients WHERE recipeID = :recipeID")
    RecipeIngredients[] loadAllRecipeIngredientsFromRecipe(int recipeID);

    @Query("SELECT * FROM Ingredients WHERE id IN " +
            "(SELECT ingredientID FROM RecipeIngredients WHERE recipeID = :recipeID)")
    Ingredient[] loadAllIngredientsFromRecipe(int recipeID);

    @Query("SELECT id FROM Ingredients WHERE name = :ingredientName")
    int getIngredientIDFromName(String ingredientName);

    @Query("SELECT * FROM StepsToFollow WHERE recipeID = :recipeID")
    StepToFollow[] loadAllStepToFollowFromRecipe(int recipeID);

    @Query("DELETE FROM Recipes WHERE id = :recipeID")
    int deleteRecipe(int recipeID);

    @Query("DELETE FROM Ingredients WHERE id = :ingredientID")
    int deleteIngredient(int ingredientID);

    @Query("DELETE FROM Ingredients WHERE id IN " +
            "(SELECT ingredientID FROM RecipeIngredients WHERE recipeID = :recipeID)")
    int deleteIngredients(int recipeID);

    @Query("DELETE FROM StepsToFollow WHERE recipeID = :recipeID AND stepNum = :stepNum")
    int deleteStep(int recipeID, int stepNum);

    @Query("DELETE FROM RecipeIngredients WHERE recipeID = :recipeID AND ingredientID = :ingredientID")
    int deleteRecipeIngredient(int recipeID, int ingredientID);

}
