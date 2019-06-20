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

/*
    Adapter para la ListView de recetas de la actividad MyRecipeList
 */

public class MyRecipeListAdapter extends BaseAdapter {

    //Textos base para la mostración de la información de la receta
    private final String RECIPE_TYPE_BASE_TEXT = "Recipe Type: ";
    private final String NUM_INGREDIENTS_BASE_TEXT = "Nº Ingredients: ";
    private final String NUM_STEPS_BASE_TEXT = "Nº Steps: ";
    private final String NUM_GUESTS_BASE_TEXT = "Nº Guests: ";
    private final String DIFFICULTY_BASE_TEXT = "Difficulty: ";

    private MyRecipesListActivity view; //Referencia a la actividad para poder llamar a métodos de la misma
    private Context context; //Referencia al contexto de la actividad para poder aplicar la layout personalizada al adapter
    private ArrayList<RecipeListItem> myRecipesList; //Lista de elementos que se muestran con la ListView

    private String recipeTypeName; //Nombre del tipo de recetas que se muestran

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

        //Se aplica el layout personalizado al elemento de la ListView
        convertView = LayoutInflater.from(context).inflate(R.layout.my_recipe_list_view_adapter, null);

        //Se obtienen los elementos con información del layout
        TextView recipeNameText = convertView.findViewById(R.id.recipeNameText);
        TextView recipeTypeText = convertView.findViewById(R.id.recipeTypeText);
        TextView nIngredientsText = convertView.findViewById(R.id.nIngredientsText);
        TextView nGuestsText = convertView.findViewById(R.id.nGuestsText);
        TextView nStepsText = convertView.findViewById(R.id.nStepsText);
        TextView difficultyText = convertView.findViewById(R.id.difficultyText);

        RatingBar recipeRatingBar = convertView.findViewById(R.id.recipeRatingBar);

        //Se asigna la información del elemento de la lista de RecipeItems a los elementos de la layout
        recipeNameText.setText(recipeItem.recipe.name);
        recipeTypeText.setText(RECIPE_TYPE_BASE_TEXT + recipeTypeName);
        nIngredientsText.setText(NUM_INGREDIENTS_BASE_TEXT + recipeItem.nIngredients);
        nStepsText.setText(NUM_STEPS_BASE_TEXT + recipeItem.nSteps);
        nGuestsText.setText(NUM_GUESTS_BASE_TEXT + recipeItem.recipe.guests);
        difficultyText.setText(DIFFICULTY_BASE_TEXT + view.getDifficultyName(recipeItem.recipe.difficultyID));

        recipeRatingBar.setRating(recipeItem.recipe.valuation);

        //Se obtienen los botones del layout
        Button deleteButton = convertView.findViewById(R.id.deleteButton);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button shoppingListButton = convertView.findViewById(R.id.youtubeButton);
        Button viewButton = convertView.findViewById(R.id.viewButton);

        //Se le asignan los onClickListener a cada uno de los botones

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecipe(recipeItem);
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

    //Recibe listas de recetas, número de ingredientes y número de pasos a seguir y convierte cada trío de elementos en un RecipeListItem
    //La lista resultante se convierte en la nueva lista de información de la ListView, y se notifica el cambio de información
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

    //Método que se llama cuando se pulsa el botón de eliminar receta: notifica a la view que se debe eliminar la receta, se elimina el item de la lista de información y se
    //notifica del cambio de información
    private void deleteRecipe(RecipeListItem recipeItem)
    {
        Recipe recipe = recipeItem.recipe;
        view.deleteRecipe(recipe);
        myRecipesList.remove(recipeItem);

        notifyDataSetChanged();
    }

    //Método que se llama cuando se pulsa el botón de editar una receta. Notifica a la view que se debe pasar a editar una receta pasándole como parámetro la receta a editar
    private void editRecipe(Recipe recipe)
    {
        view.editRecipe(recipe);
    }

    //Método que se llama cuando se pulsa el bóton de búsqueda en YouTube. Notifica a la view que se debe pasar a buscar la receta en YouTube
    private void searchOnYoutube(Recipe recipe)
    {
        view.searchOnYoutube(recipe);
    }

    //Método que se llama cuando se pulsa el bóton de Ver con detalle. Notifica a la view que se debe pasar a ver con detalle la receta que se pasa por parámetro
    private void viewRecipe(Recipe recipe)
    {
        view.showRecipe(recipe);
    }

    //Clase privada útil para manejar los datos sobre la receta que se deben mostrar. Contiene la receta en sí, el número de ingredientes y el número de pasos a seguir
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
