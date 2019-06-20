package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecipeDao {

    //Insert methods

    //Inserta en la tabla Recipes una receta a partir de un objeto Recipe y devuelve el ID de la fila en la que ha quedado insertado
    @Insert
    long insertRecipe(Recipe recipe);

    //Inserta en la tabla Ingredients un ingrediente a partir de un objeto Ingredient y devuelve el ID de la fila en la que ha quedado insertado
    @Insert
    long insertIngredient(Ingredient ingredient);

    //Inserta en la tabla Ingredients un grupo de ingredientes a partir de un array de objetos Ingredient y devuelve un array con los IDs de la fila en la que han quedado
    // insertados cada uno de los Ingredients
    @Insert
    long[] insertIngredients(Ingredient... ingredients);

    //Inserta en la tabla StepsToFollow un paso a seguir a partir de un objeto StepToFollow
    @Insert
    void insertStep(StepToFollow stepToFollow);

    //Inserta en la tabla StepsToFollow un grupo de pasos a seguir a partir de un array de objetos StepToFollow
    @Insert
    void insertSteps(StepToFollow... steps);

    //Inserta en la tabla RecipeIngredients un grupo de relaciones entre una receta y un ingrediente a partir de un array de objetos RecipeIngredients
    @Insert
    void insertRecipeIngredients(RecipeIngredients... recipeIngredients);

    //Select methods

    //Obtiene el ID más alto de las recetas insertadas en la tabla Recipes
    @Query("SELECT MAX(id) FROM Recipes")
    int lastID();

    //Obtiene el número de ingredientes existentes con el nombre pasado por parámetro. Si es 0, significa que el ingrediente no existe en la tabla Ingredients
    @Query("SELECT count(*) FROM Ingredients WHERE name = :ingredientName")
    int checkIngredientExists(String ingredientName);

    //Obtiene el número de relaciones entre receta e ingrediente dado un ID de una receta, es decir, el número de ingredientes de una receta
    @Query("SELECT count(*) FROM RecipeIngredients WHERE recipeID = :recipeID")
    int nIngredientsOfRecipe(int recipeID);

    //Obtiene el número de pasos a seguir dado un ID de una receta, es decir, el número de pasos a seguir de una receta
    @Query("SELECT count(*) FROM StepsToFollow WHERE recipeID = :recipeID")
    int nStepsOfRecipe(int recipeID);

    //Obtiene un objeto Recipe a partir del ID de la receta
    @Query("SELECT * FROM Recipes WHERE id = :recipeID")
    Recipe loadRecipeByID(int recipeID);

    //Obtiene las recetas cuyo ID del tipo es igual al pasado por parámetro, es decir, las recetas de ese tipo
    @Query("SELECT * FROM Recipes WHERE typeID = :recipeType")
    Recipe[] loadAllRecipesOfType(int recipeType);

    //Obtiene las relaciones entre receta e ingrediente dado la ID de la receta
    @Query("SELECT * FROM RecipeIngredients WHERE recipeID = :recipeID")
    RecipeIngredients[] loadAllRecipeIngredientsFromRecipe(int recipeID);

    //Obtiene los ingredientes dado el ID de una receta, es decir, los ingredientes de esa receta, pasando por la tabla RecipeIngredients para filtrar por las relaciones con
    //ingredientes de esa receta
    @Query("SELECT * FROM Ingredients WHERE id IN " +
            "(SELECT ingredientID FROM RecipeIngredients WHERE recipeID = :recipeID)")
    Ingredient[] loadAllIngredientsFromRecipe(int recipeID);

    //Obtiene el ID de un ingrediente a partir de su nombre
    @Query("SELECT id FROM Ingredients WHERE name = :ingredientName")
    int getIngredientIDFromName(String ingredientName);

    //Obtiene pasos a seguir a partir del ID de una receta, es decir, los pasos a seguir de una receta
    @Query("SELECT * FROM StepsToFollow WHERE recipeID = :recipeID")
    StepToFollow[] loadAllStepToFollowFromRecipe(int recipeID);

    //Update methods

    //Actualiza la información de una receta a partir de su ID
    @Query("UPDATE Recipes " +
            "SET name = :recipeName, typeID = :recipeTypeID, guests = :recipeGuests, valuation = :recipeValuation, difficultyID = :recipeDifficultyID " +
            "WHERE id = :recipeID")
    void updateRecipe(int recipeID, String recipeName, int recipeTypeID, int recipeGuests, float recipeValuation, int recipeDifficultyID);

    //Delete methods

    //Elimina una receta a partir de su ID
    @Query("DELETE FROM Recipes WHERE id = :recipeID")
    int deleteRecipe(int recipeID);

    //Elimina un paso a seguir a partir del ID de la receta a la cual pertenece y de su número dentro de la receta
    @Query("DELETE FROM StepsToFollow WHERE recipeID = :recipeID AND stepNum = :stepNum")
    int deleteStep(int recipeID, int stepNum);

    //Elimina una relación entre receta e ingrediente a partir de los IDs de la receta y el ingrediente
    @Query("DELETE FROM RecipeIngredients WHERE recipeID = :recipeID AND ingredientID = :ingredientID")
    int deleteRecipeIngredient(int recipeID, int ingredientID);

}
