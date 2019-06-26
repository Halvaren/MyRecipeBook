package alvaro.sabi.rosquilletas.myrecipebook.showRecipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.interfaces.ShowRecipeInterface;
import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.database.Recipe;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/*
    Actividad que muestra una receta en concreto en detalle. Dicha receta se ha seleccionado con anterioridad en MyRecipesListActivity
 */

public class ShowRecipeActivity extends AppCompatActivity implements ShowRecipeInterface {

    // ATRIBUTOS

    //Título de la actividad
    public final String ACTIVITY_TITLE = "My Recipe";

    //Textos base para el texto de información de los ingredientes
    public final String INGREDIENTS_INFO_FIRST_PART = "Ingredients (";
    public final String INGREDIENTS_INFO_SECOND_PART1 = " guest):";
    public final String INGREDIENTS_INFO_SECOND_PART2 = " guests):";

    //Receta a mostrar
    private Recipe recipe;
    //Ingredientes de la receta a mostrar
    private ArrayList<String> ingredients;
    //Pasos de la receta a mostrar
    private ArrayList<String> steps;

    //Presenter
    private ShowRecipePresenter presenter;

    //Elementos de la view
    private TextView recipeNameText;
    private RatingBar recipeRating;
    private TextView recipeTypeText;
    private TextView recipeDifficultyText;
    private TextView recipeIngredientsInfo;
    private ListView recipeIngredientListView;
    private ListView recipeStepListView;

    //Adapters para las ListViews
    private ShowIngredientStepListAdapter ingredientAdapter;
    private ShowIngredientStepListAdapter stepAdapter;

    // MÉTODOS

    // Manejo de los estados de la actividad

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe_activity);

        presenter = new ShowRecipePresenter(this, this);

        //Obtenemos los elementos de la view
        recipeNameText = findViewById(R.id.showRecipeName);
        recipeRating = findViewById(R.id.showRecipeRating);
        recipeTypeText = findViewById(R.id.showRecipeTypeText);
        recipeDifficultyText = findViewById(R.id.showRecipeDifficultyText);
        recipeIngredientsInfo = findViewById(R.id.showRecipeIngredientsInfo);
        recipeIngredientListView = findViewById(R.id.showRecipeIngredientsList);
        recipeStepListView = findViewById(R.id.showRecipeStepsList);

        //Creación y asignación de los adapters
        ingredientAdapter = new ShowIngredientStepListAdapter(this);
        recipeIngredientListView.setAdapter(ingredientAdapter);

        stepAdapter = new ShowIngredientStepListAdapter(this);
        recipeStepListView.setAdapter(stepAdapter);

        //Desactivamos las barras de división de elementos de las ListView por estética
        recipeIngredientListView.setDivider(null);
        recipeStepListView.setDivider(null);

        //Se obtiene la receta a partir del Intent
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("Recipe");
        setRecipe();

        //En el caso de estar recuperando la información del estado
        if(savedInstanceState != null)
        {
            //Se obtienen del paquete las listas de ingredientes y de pasos
            ingredients = savedInstanceState.getStringArrayList("Ingredients");
            steps = savedInstanceState.getStringArrayList("Steps");

            //Comprobamos que efectivamente se ha obtenido algo
            if(ingredients != null && steps != null)
            {
                //Y si es así, se asignan mediante los dos métodos siguientes
                setIngredientList(ingredients);
                setStepList(steps);
            }
            else
            {
                //Si no se ha obtenido null, se piden al presenter ambas listas
                presenter.requestIngredientList(recipe.id);
                presenter.requestStepList(recipe.id);
            }
        }
        //Igualmente, si no existe paquete del cual recuperar la información del estado
        else
        {
            //También se piden al presenter ambas listas
            presenter.requestIngredientList(recipe.id);
            presenter.requestStepList(recipe.id);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //Se activa el botón de Atrás de la Action Bar
        actionBar.setTitle(ACTIVITY_TITLE); //Y se le asigna el título a la actividad
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        if(ingredients != null) savedInstanceState.putStringArrayList("Ingredients", ingredients);
        if(steps != null) savedInstanceState.putStringArrayList("Steps", steps);
    }

    //Este método se llama al interactuar con alguno de los elementos de la Action Bar, pero únicamente está activado el botón de Atrás
    public boolean onOptionsItemSelected(MenuItem item) {
        //Se vuelve a la actividad anterior
        finish();
        return true;
    }


    // Setters

    //Método que asigna a los elementos de la view los valores de los parámetros de la receta
    public void setRecipe() {

        recipeNameText.setText(recipe.name);
        recipeRating.setRating(recipe.valuation);
        recipeTypeText.setText(presenter.getRecipeTypeName(recipe.typeID));
        recipeDifficultyText.setText(presenter.getDifficultyName(recipe.difficultyID));
        if(recipe.guests > 1) //Esto permite utilizar el plural en caso de haber más de 1 un comensal, o el singular en caso contrario
            recipeIngredientsInfo.setText(INGREDIENTS_INFO_FIRST_PART + recipe.guests + INGREDIENTS_INFO_SECOND_PART2);
        else
            recipeIngredientsInfo.setText(INGREDIENTS_INFO_FIRST_PART + recipe.guests + INGREDIENTS_INFO_SECOND_PART1);
    }

    //Método que asigna a la ListView de los ingredientes la lista de valores
    public void setIngredientList(ArrayList<String> value) {

        ingredients = value;
        ingredientAdapter.setList(value);
    }

    //Método que asigna a la ListView de los pasos la lista de valores
    public void setStepList(ArrayList<String> value) {

        steps = value;
        stepAdapter.setList(value);
    }
}
