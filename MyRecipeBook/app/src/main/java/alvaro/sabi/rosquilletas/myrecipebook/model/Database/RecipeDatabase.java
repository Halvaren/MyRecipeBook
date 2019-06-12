package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RecipeType.class, Recipe.class, StepToFollow.class, Ingredient.class, RecipeIngredients.class, Menu.class, ShoppingList.class}, version = 2)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}
