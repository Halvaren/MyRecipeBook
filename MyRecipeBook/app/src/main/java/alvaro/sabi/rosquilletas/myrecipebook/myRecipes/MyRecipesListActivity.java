package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Ingredient;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.EditRecipeActivity;
import androidx.appcompat.app.AppCompatActivity;

public class MyRecipesListActivity extends AppCompatActivity {

    private ListView myRecipesListView;

    private MyRecipesListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipes_list_activity);

        presenter = new MyRecipesListPresenter(this, this);

        Intent intent = getIntent();
        Log.d("Intent", String.valueOf(intent == null));
        int recipeType = intent.getIntExtra("RecipeType", 0);

        String recipeTypeName = getRecipeTypeName(recipeType);

        myRecipesListView = findViewById(R.id.myRecipesListView);
        MyRecipeListAdapter adapter = new MyRecipeListAdapter(this, this, recipeTypeName);
        myRecipesListView.setAdapter(adapter);

        requestRecipeList(recipeType);
    }

    public String getRecipeTypeName(int recipeType)
    {
        return presenter.getRecipeTypeName(recipeType);
    }

    public String getDifficultyName(int difficultyID)
    {
        return presenter.getDifficultyName(difficultyID);
    }

    private void requestRecipeList(int recipeType)
    {
        presenter.requestRecipeList(recipeType);
    }

    public void setRecipeList(Recipe[] recipeList, Integer[] ingredients, Integer[] steps) {
        ((MyRecipeListAdapter) myRecipesListView.getAdapter()).setMyRecipesList(recipeList, ingredients, steps);
    }

    public void editRecipe(Recipe recipe) {
        Intent intent = new Intent(MyRecipesListActivity.this, EditRecipeActivity.class);
        intent.putExtra("EditRecipeID", recipe.id);

        startActivity(intent);
    }

    public void deleteRecipe(Recipe recipe)
    {
        presenter.deleteRecipe(recipe);
    }

    public void createShoppingList(Recipe recipe) {
    }

    public void showRecipe(Recipe recipe) {
        Intent intent = new Intent(MyRecipesListActivity.this, ShowRecipeActivity.class);
        intent.putExtra("Recipe", recipe);

        startActivity(intent);
    }
}
