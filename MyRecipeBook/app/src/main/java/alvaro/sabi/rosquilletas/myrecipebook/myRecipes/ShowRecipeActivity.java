package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ShowRecipeActivity extends AppCompatActivity {

    public final String ACTIVITY_TITLE = "My Recipe";

    public final String INGREDIENTS_INFO_FIRST_PART = "Ingredients (";
    public final String INGREDIENTS_INFO_SECOND_PART1 = " guest):";
    public final String INGREDIENTS_INFO_SECOND_PART2 = " guests):";

    private Recipe recipe;

    private ShowRecipePresenter presenter;

    private TextView recipeNameText;
    private RatingBar recipeRating;
    private TextView recipeTypeText;
    private TextView recipeDifficultyText;
    private TextView recipeIngredientsInfo;
    private ListView recipeIngredientListView;
    private ListView recipeStepListView;

    private ShowIngredientStepListAdapter ingredientAdapter;
    private ShowIngredientStepListAdapter stepAdapter;

    private ArrayList<String> ingredients;
    private ArrayList<String> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe_activity);

        presenter = new ShowRecipePresenter(this, this);

        recipeNameText = findViewById(R.id.showRecipeName);
        recipeRating = findViewById(R.id.showRecipeRating);
        recipeTypeText = findViewById(R.id.showRecipeTypeText);
        recipeDifficultyText = findViewById(R.id.showRecipeDifficultyText);
        recipeIngredientsInfo = findViewById(R.id.showRecipeIngredientsInfo);
        recipeIngredientListView = findViewById(R.id.showRecipeIngredientsList);
        recipeStepListView = findViewById(R.id.showRecipeStepsList);

        ingredientAdapter = new ShowIngredientStepListAdapter(this);
        recipeIngredientListView.setAdapter(ingredientAdapter);

        stepAdapter = new ShowIngredientStepListAdapter(this);
        recipeStepListView.setAdapter(stepAdapter);

        recipeIngredientListView.setDivider(null);
        recipeStepListView.setDivider(null);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("Recipe");
        setRecipe();

        if(savedInstanceState != null)
        {
            ingredients = savedInstanceState.getStringArrayList("Ingredients");
            steps = savedInstanceState.getStringArrayList("Steps");

            if(ingredients != null && steps != null)
            {
                setIngredientList(ingredients);
                setStepList(steps);
            }
            else
            {
                presenter.requestIngredientList(recipe.id);
                presenter.requestStepList(recipe.id);
            }
        }
        else
        {
            presenter.requestIngredientList(recipe.id);
            presenter.requestStepList(recipe.id);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(ACTIVITY_TITLE);
    }

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        if(ingredients != null) savedInstanceState.putStringArrayList("Ingredients", ingredients);
        if(steps != null) savedInstanceState.putStringArrayList("Steps", steps);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        finish();
        return true;
    }

    public void setRecipe()
    {
        recipeNameText.setText(recipe.name);
        recipeRating.setRating(recipe.valuation);
        recipeTypeText.setText(presenter.getRecipeTypeName(recipe.typeID));
        recipeDifficultyText.setText(presenter.getDifficultyName(recipe.difficultyID));
        if(recipe.guests > 1)
            recipeIngredientsInfo.setText(INGREDIENTS_INFO_FIRST_PART + recipe.guests + INGREDIENTS_INFO_SECOND_PART2);
        else
            recipeIngredientsInfo.setText(INGREDIENTS_INFO_FIRST_PART + recipe.guests + INGREDIENTS_INFO_SECOND_PART1);
    }

    public void setIngredientList(ArrayList<String> value)
    {
        ingredients = value;
        ingredientAdapter.setList(value);
    }

    public void setStepList(ArrayList<String> value)
    {
        steps = value;
        stepAdapter.setList(value);
    }
}
