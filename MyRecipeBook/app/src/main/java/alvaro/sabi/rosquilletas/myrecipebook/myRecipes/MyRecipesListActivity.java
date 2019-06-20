package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeIntents;

import alvaro.sabi.rosquilletas.myrecipebook.Interfaces.MyRecipeListInterface;
import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.Interfaces.ToastMessages;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.DeleteRecipeDialog;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.EditRecipeActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/*
    Actividad que muestra las recetas de un cierto tipo seleccionado previamente en la actividad RecipeType
 */

public class MyRecipesListActivity extends AppCompatActivity implements ToastMessages, MyRecipeListInterface {

    private ListView myRecipesListView; //ListView que muestra las recetas

    private MyRecipesListPresenter presenter;

    private Recipe[] recipeList; //Lista de recetas
    private int[] ingredients; //Lista de números de ingredientes de cada receta
    private int[] steps; //Lista de números de pasos de cada receta

    private MyRecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipes_list_activity);

        presenter = new MyRecipesListPresenter(this, this);

        //De la anterior activdiad se recibe el ID del tipo de receta del cual mostrar las recetas guardadas
        Intent intent = getIntent();
        int recipeType = intent.getIntExtra("RecipeType", 0);

        String recipeTypeName = getRecipeTypeName(recipeType); //Se obtiene el nombre del tipo de receta

        //Se obtiene el elemento ListView de la layout
        myRecipesListView = findViewById(R.id.myRecipesListView);
        //Se crea y asigna el correspondiente adapter (pasándole el nombre del tipo de receta) a la ListView
        adapter = new MyRecipeListAdapter(this, this, recipeTypeName);
        myRecipesListView.setAdapter(adapter);

        //Se comprueba si venimos de un cambio de estado de la actividad
        if(savedInstanceState != null)
        {
            //En caso afirmativo, se obtiene la información necesaria

            //Se obtiene la lista de recetas,
            recipeList = (Recipe[]) savedInstanceState.getParcelableArray("RecipeList");
            //La lista de números de ingredientes de cada receta
            ingredients = savedInstanceState.getIntArray("Ingredients");
            //Y la lista de números de pasos de cada receta
            steps = savedInstanceState.getIntArray("Steps");

            //Si ninguna de las listas solicitadas resulta ser un null, quiere decir que se tiene toda la información
            if(recipeList != null && ingredients != null && steps != null)
            {
                //En ese caso se puede aplicar la información recibida para que se muestre
                setRecipeList(recipeList, ingredients, steps);
            }
            //En caso contrario, cabe solicidar la información
            else requestRecipeList(recipeType);
        }
        //Igualmente, si no se viene de un cambio de estado, también se debe solicitar la información
        else requestRecipeList(recipeType);

        ActionBar actionBar = getSupportActionBar();
        //Activamos el botón de Atrás en la barra superior
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Cambiamos el título con el nombre de tipo de receta del cual se ha solicitado ver las recetas guardadas
        actionBar.setTitle(recipeTypeName);
    }

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        //Se guarda en el paquete de savedInstanceState las listas de recetas, números de ingredientes y números de pasos
        if(recipeList != null) savedInstanceState.putParcelableArray("RecipeList", recipeList);
        if(ingredients != null) savedInstanceState.putIntArray("Ingredients", ingredients);
        if(steps != null) savedInstanceState.putIntArray("Steps", steps);
    }

    public void onResume() {

        super.onResume();

        //Cuando se vuelve a reanudar la actividad (tras haber editado una receta por ejemplo),
        Intent intent = getIntent();
        int recipeType = intent.getIntExtra("RecipeType", 0);

        //Se vuelve a solicitar la información sobre las recetas
        requestRecipeList(recipeType);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        finish(); //Se vuelve a la anterior actividad
        return true;
    }

    //Método que solicita al presenter el nombre de un tipo de receta a partir de su ID
    public String getRecipeTypeName(int recipeType)
    {
        return presenter.getRecipeTypeName(recipeType);
    }

    //Método que solicita al presenter el nombre de una dificultad a partir de su ID
    public String getDifficultyName(int difficultyID)
    {
        return presenter.getDifficultyName(difficultyID);
    }

    //Método que solicita al presenter que prepare la lista de recetas a mostrar
    public void requestRecipeList(int recipeType)
    {
        presenter.requestRecipeList(recipeType);
    }

    //Método que será llamado desde el presenter cuando esté disponga de la lista de recetas, del número de ingredientes por receta y
    //del número de pasos por receta
    public void setRecipeList(Recipe[] recipeList, int[] ingredients, int[] steps) {
        this.recipeList = recipeList;
        this.ingredients = ingredients;
        this.steps = steps;

        //Se envía la información al adapter
        ((MyRecipeListAdapter) myRecipesListView.getAdapter()).setMyRecipesList(recipeList, ingredients, steps);

        //Si la lista está vacía, se muestra un Toast con cierta información
        if(recipeList.length == 0) showToast(emptyRecipeList);
    }

    //Método que se llama desde el adapter cuando se pulsa el botón de editar de uno de los elementos de la ListView
    public void editRecipe(Recipe recipe) {
        Intent intent = new Intent(MyRecipesListActivity.this, EditRecipeActivity.class);
        intent.putExtra("EditRecipeID", recipe.id);

        startActivity(intent);
    }

    //Método que se llama desde el dialog cuando el usuario confirma que quiere borrar la receta: avisa tanto al presenter como
    //al adapter que deben borrar la receta
    public void deleteRecipe(Recipe recipe, int i)
    {
        presenter.deleteRecipe(recipe);
        adapter.deleteRecipe(i);
    }

    //Método que se llama desde el adapter cuando se pulsa el botón de búsqueda en YT de uno de los elementos de la ListView
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

    //Método que se llama desde el adapter cuando se pulsa el botón de ver en detalle de uno de los elementos de la ListView
    public void showRecipe(Recipe recipe) {
        Intent intent = new Intent(MyRecipesListActivity.this, ShowRecipeActivity.class);
        intent.putExtra("Recipe", recipe);

        startActivity(intent);
    }

    //Método para mostrar por pantalla información mediante un Toast
    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Método que genera un dialog para comprobar si el usuario quiere borrar una receta o no
    public void showDeleteRecipeDialog(Recipe recipe, int i)
    {
        DeleteRecipeDialog dialog = new DeleteRecipeDialog(this, recipe, i, this, getRecipeTypeName(recipe.typeID));
        dialog.show(getSupportFragmentManager(), "delete");
    }
}
