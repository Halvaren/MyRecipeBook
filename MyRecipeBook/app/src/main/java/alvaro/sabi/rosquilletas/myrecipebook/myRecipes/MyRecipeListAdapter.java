package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;

public class MyRecipeListAdapter extends BaseAdapter {

    private final String RECIPE_TYPE_BASE_TEXT = "Recipe Type: ";
    private final String NUM_INGREDIENTS_BASE_TEXT = "Nº Ingredients: ";
    private final String NUM_STEPS_BASE_TEXT = "Nº Steps: ";
    private final String NUM_GUESTS_BASE_TEXT = "Nº Guests: ";
    private final String DIFFICULTY_BASE_TEXT = "Difficulty: ";

    private MyRecipesListActivity view;
    private Context context;
    private ArrayList<RecipeListItem> myRecipesList;

    private String recipeTypeName;

    public boolean recipeDeleted = false;

    public MyRecipeListAdapter(MyRecipesListActivity view, Context context, String recipeTypeName)
    {
        this.view = view;
        this.context = context;
        this.recipeTypeName = recipeTypeName;

        myRecipesList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return myRecipesList.size();
    }

    @Override
    public Object getItem(int position) {
        return myRecipesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final RecipeListItem recipeItem = (RecipeListItem) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.my_recipe_list_view_adapter, null);

        TextView recipeNameText = convertView.findViewById(R.id.recipeNameText);
        TextView recipeTypeText = convertView.findViewById(R.id.recipeTypeText);
        TextView nIngredientsText = convertView.findViewById(R.id.nIngredientsText);
        TextView nGuestsText = convertView.findViewById(R.id.nGuestsText);
        TextView nStepsText = convertView.findViewById(R.id.nStepsText);
        TextView difficultyText = convertView.findViewById(R.id.difficultyText);

        RatingBar recipeRatingBar = convertView.findViewById(R.id.recipeRatingBar);

        recipeNameText.setText(recipeItem.recipe.name);
        recipeTypeText.setText(RECIPE_TYPE_BASE_TEXT + recipeTypeName);
        nIngredientsText.setText(NUM_INGREDIENTS_BASE_TEXT + recipeItem.nIngredients);
        nStepsText.setText(NUM_STEPS_BASE_TEXT + recipeItem.nSteps);
        nGuestsText.setText(NUM_GUESTS_BASE_TEXT + recipeItem.recipe.guests);
        difficultyText.setText(DIFFICULTY_BASE_TEXT + view.getDifficultyName(recipeItem.recipe.difficultyID));

        recipeRatingBar.setRating(recipeItem.recipe.valuation);

        Button deleteButton = convertView.findViewById(R.id.deleteButton);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button shoppingListButton = convertView.findViewById(R.id.youtubeButton);
        Button viewButton = convertView.findViewById(R.id.viewButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(recipeItem, position);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRecipe(recipeItem.recipe);
            }
        });

        shoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchOnYoutube(recipeItem.recipe);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecipe(recipeItem.recipe);
            }
        });

        return convertView;
    }

    public void setMyRecipesList(Recipe[] recipes, int[] ingredients, int[] steps)
    {
        ArrayList<RecipeListItem> newList = new ArrayList<>();
        for(int i = 0; i < recipes.length; i++)
        {
            newList.add(new RecipeListItem(recipes[i], ingredients[i], steps[i]));
        }

        myRecipesList = newList;

        notifyDataSetChanged();
    }

    private void showDeleteDialog(RecipeListItem recipeItem, int i)
    {
        Recipe recipe = recipeItem.recipe;
        view.showDialog(recipe, i);

        //if (recipeDeleted == true) myRecipesList.remove(recipeItem);
        Log.d("lk", Boolean.toString(recipeDeleted));

       // notifyDataSetChanged();
    }

    public void deleteRecipe(int i){
        myRecipesList.remove(i);
        notifyDataSetChanged();

    }

    private void editRecipe(Recipe recipe)
    {
        view.editRecipe(recipe);
    }

    private void searchOnYoutube(Recipe recipe)
    {
        view.searchOnYoutube(recipe);
    }

    private void viewRecipe(Recipe recipe)
    {
        view.showRecipe(recipe);
    }

    private class RecipeListItem
    {
        Recipe recipe;
        int nIngredients;
        int nSteps;

        public RecipeListItem(Recipe recipe, int nIngredients, int nSteps)
        {
            this.recipe = recipe;
            this.nIngredients = nIngredients;
            this.nSteps = nSteps;
        }
    }
}
