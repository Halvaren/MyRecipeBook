package alvaro.sabi.rosquilletas.myrecipebook.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Recipe.class, StepToFollow.class, Ingredient.class, RecipeIngredients.class}, version = 11)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}
