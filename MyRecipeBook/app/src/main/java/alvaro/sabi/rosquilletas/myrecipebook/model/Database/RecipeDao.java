package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecipeDao {

    @Insert
    void insertRecipeTypeNames(RecipeType... recipeType);

    @Query("SELECT name FROM RecipeType")
    String[] loadAllRecipeTypeNames();

    @Query("SELECT count(*) FROM RecipeType")
    int recipeTypeNamesNumberOfRows();

    @Query("SELECT * FROM Recipes")
    Recipe[] loadAllRecipes();

    @Query("SELECT * FROM Recipes WHERE type = :recipeType")
    Recipe[] loadAllRecipesOfType(String recipeType);

    @Query("SELECT * FROM Ingredients WHERE name IN " +
            "(SELECT ingredientName FROM RecipeIngredients WHERE recipeName = :recipeName)")
    Ingredient[] loadAllIngredientsFromRecipe(String recipeName);

    @Query("SELECT * FROM StepsToFollow WHERE recipeName = :recipeName")
    StepToFollow[] loadAllStepToFollowFromRecipe(String recipeName);

    @Delete
    int deleteRecipe(Recipe recipe);

}
