package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;
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
    private ArrayList<Recipe> myRecipesList;

    private String recipeTypeName;

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
        final Recipe recipe = (Recipe) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.my_recipe_list_view_adapter, null);

        TextView recipeNameText = convertView.findViewById(R.id.recipeNameText);
        TextView recipeTypeText = convertView.findViewById(R.id.recipeTypeText);
        TextView nIngredientsText = convertView.findViewById(R.id.nIngredientsText);
        TextView nGuestsText = convertView.findViewById(R.id.nGuestsText);
        TextView nStepsText = convertView.findViewById(R.id.nStepsText);
        TextView difficultyText = convertView.findViewById(R.id.difficultyText);

        RatingBar recipeRatingBar = convertView.findViewById(R.id.recipeRatingBar);

        recipeNameText.setText(recipe.name);
        recipeTypeText.setText(RECIPE_TYPE_BASE_TEXT + recipeTypeName);
        nIngredientsText.setText(NUM_INGREDIENTS_BASE_TEXT + recipe.getIngredientList().size());
        nStepsText.setText(NUM_STEPS_BASE_TEXT + recipe.getStepList().size());
        nGuestsText.setText(NUM_GUESTS_BASE_TEXT + recipe.guests);
        difficultyText.setText(DIFFICULTY_BASE_TEXT + view.getDifficultyName(recipe.difficultyID));

        recipeRatingBar.setRating(recipe.valuation);

        Button deleteButton = convertView.findViewById(R.id.deleteButton);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button shoppingListButton = convertView.findViewById(R.id.shoppingListButton);
        Button viewButton = convertView.findViewById(R.id.viewButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecipe(recipe);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRecipe(recipe);
            }
        });

        shoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShoppingList(recipe);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecipe(recipe);
            }
        });

        return convertView;
    }

    public void setMyRecipesList(ArrayList<Recipe> list)
    {
        myRecipesList = list;

        notifyDataSetChanged();
    }

    private void deleteRecipe(Recipe recipe)
    {
        view.deleteRecipe(recipe);
        myRecipesList.remove(recipe);
    }

    private void editRecipe(Recipe recipe)
    {
        view.editRecipe(recipe);
    }

    private void createShoppingList(Recipe recipe)
    {
        view.createShoppingList(recipe);
    }

    private void viewRecipe(Recipe recipe)
    {
        view.showRecipe(recipe);
    }
}
