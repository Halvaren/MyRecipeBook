package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeIntents;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.ToastMessages;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.EditRecipeActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MyRecipesListActivity extends AppCompatActivity implements ToastMessages {

    private ListView myRecipesListView;

    private MyRecipesListPresenter presenter;

    private Recipe[] recipeList;
    private int[] ingredients;
    private int[] steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipes_list_activity);

        presenter = new MyRecipesListPresenter(this, this);

        Intent intent = getIntent();
        int recipeType = intent.getIntExtra("RecipeType", 0);

        String recipeTypeName = getRecipeTypeName(recipeType);

        myRecipesListView = findViewById(R.id.myRecipesListView);
        MyRecipeListAdapter adapter = new MyRecipeListAdapter(this, this, recipeTypeName);
        myRecipesListView.setAdapter(adapter);

        if(savedInstanceState != null)
        {
            recipeList = (Recipe[]) savedInstanceState.getParcelableArray("RecipeList");
            ingredients = savedInstanceState.getIntArray("Ingredients");
            steps = savedInstanceState.getIntArray("Steps");

            if(recipeList != null && ingredients != null && steps != null)
            {
                setRecipeList(recipeList, ingredients, steps);
            }
            else requestRecipeList(recipeType);
        }
        else requestRecipeList(recipeType);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(recipeTypeName);
    }

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        if(recipeList != null) savedInstanceState.putParcelableArray("RecipeList", recipeList);
        if(ingredients != null) savedInstanceState.putIntArray("Ingredients", ingredients);
        if(steps != null) savedInstanceState.putIntArray("Steps", steps);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        finish();
        return true;
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

    public void setRecipeList(Recipe[] recipeList, int[] ingredients, int[] steps) {
        this.recipeList = recipeList;
        this.ingredients = ingredients;
        this.steps = steps;

        ((MyRecipeListAdapter) myRecipesListView.getAdapter()).setMyRecipesList(recipeList, ingredients, steps);

        if(recipeList.length == 0) showToast(emptyRecipeList);
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

    public void searchOnYoutube(Recipe recipe) {
        if(YouTubeIntents.canResolveSearchIntent(this))
        {
            Intent intent = YouTubeIntents.createSearchIntent(this, recipe.name + " recipe");
            startActivity(intent);
        }
        else
        {
            showToast(youtubeVersion);
        }
    }

    public void showRecipe(Recipe recipe) {
        Intent intent = new Intent(MyRecipesListActivity.this, ShowRecipeActivity.class);
        intent.putExtra("Recipe", recipe);

        startActivity(intent);
    }

    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
