package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.Interfaces.EditRecipeInterface;
import alvaro.sabi.rosquilletas.myrecipebook.MainActivity;
import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.Interfaces.ToastMessages;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.IngredientStepListAdapter;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.MyRecipesListActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EditRecipeActivity extends AppCompatActivity implements ToastMessages, EditRecipeInterface {

    // ATRIBUTOS

    //Título de la actividad si la receta es nueva
    public final String NEW_RECIPE_ACTIVITY_TITLE = "New Recipe";
    //Título de la actividad si se está editando la receta
    public final String EDIT_RECIPE_ACTIVITY_TITLE = "Edit Recipe";
    //Texto base para el campo de número de comensales
    public final String NUMBER_GUESTS_BASE_TEXT = "Number of guests: ";

    //Presenter
    private EditRecipePresenter presenter;

    //Elementos de la view
    private EditText recipeName;
    private Spinner recipeType;
    private TextView numGuestsText;
    private SeekBar numGuestsSeekBar;

    private ListView ingredientList;
    private ListView stepList;

    private RatingBar valuation;
    private Spinner difficulty;

    //Adapters para las ListViews
    private IngredientStepListAdapter ingredientListAdapter;
    private IngredientStepListAdapter stepListAdapter;

    //ID de la receta que se está editando actualmente
    private int currentRecipeID;

    //Indica si se está editando o no
    private boolean editingRecipe;

    // MÉTODOS

    // Manejo de los estados de la actividad

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe_activity);

        //Se inicializa en -1 como valor por defecto, indicando que la receta aún no está incluida en la base de datos
        //En caso de que se esté editando la receta, posteriormente, se le asignará el valor correcto
        currentRecipeID = -1;

        presenter = new EditRecipePresenter(this, this);

        //Obtención de los elementos de la view
        recipeName = findViewById(R.id.editRecipeNameField);
        recipeType = findViewById(R.id.editRecipeTypeSpinner);
        numGuestsText = findViewById(R.id.editRecipeNGuestsText);
        numGuestsSeekBar = findViewById(R.id.editRecipeNGuestsSeekBar);

        ingredientList = findViewById(R.id.editRecipeIngredientList);
        stepList = findViewById(R.id.editRecipeStepList);

        valuation = findViewById(R.id.editRecipeRatingBar);
        difficulty = findViewById(R.id.editRecipeDifficultySpinner);

        //Se crean y asignan los adapter para las ListViews
        ingredientListAdapter = new IngredientStepListAdapter(this, this, ingredientList);
        ingredientList.setAdapter(ingredientListAdapter);

        stepListAdapter = new IngredientStepListAdapter(this, this, stepList);
        stepList.setAdapter(stepListAdapter);

        //Se crean y asignan los adapter para los Spinners
        ArrayAdapter<String> recipeTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presenter.getRecipeTypeNames());
        recipeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeType.setAdapter(recipeTypeAdapter);

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presenter.getDifficultyNames());
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultyAdapter);

        //Se le asigna un Listener a la SeekBar para detectar cuando cambia
        numGuestsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarUpdated();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarUpdated();

        editingRecipe = false;

        //Se comprueba si venimos de un cambio de estado de la actividad
        if(savedInstanceState != null)
        {
            //En caso afirmativo, se obtiene la información necesaria

            //La receta (contiene información del nombre, el tipo, el número de comensales...)
            Recipe recipe = savedInstanceState.getParcelable("CurrentRecipe");
            //Si se estaba editando este valor será distinto a -1; si no, seguirá siendo -1
            currentRecipeID = savedInstanceState.getInt("CurrentRecipeID");
            //Este método asignará a los campos los valores correctos a partir de la receta obtenida
            setRecipe(recipe);

            //Se obtienen y asignan las listas de ingredientes y pasos a partir del paquete
            setIngredientList(savedInstanceState.getStringArrayList("IngredientList"));
            setStepList(savedInstanceState.getStringArrayList("StepList"));

            //Se guarda en editingRecipe si veníamos de editar o de crear una nueva receta
            editingRecipe = savedInstanceState.getBoolean("Editing");
        }
        else
        {
            //En caso contario, suponiendo que venimos de otra actividad, obtenemos el Intent para obtener valores que nos interesan
            Intent intent = getIntent();
            //El valor que nos interesa es la ID de la receta, ya que si es distinto a -1 quiere decir que venimos de elegir editar una receta ya existente
            //Si es igual a -1, significa que estamos con una nueva receta
            currentRecipeID = intent.getIntExtra("EditRecipeID", -1);
            if(currentRecipeID != -1)
            {
                //Si es distinto a -1, pedimos al presenter que nos devuelva la receta, sus ingredientes y sus pasos a partir del ID de la receta
                presenter.getRecipeByID(currentRecipeID);
                presenter.getIngredientListFromRecipe(currentRecipeID);
                presenter.getStepListFromRecipe(currentRecipeID);

                //Sabemos que estamos editando, por lo que cambiamos el valor del booleano
                editingRecipe = true;
            }
            else
            {
                //En cualquier otro caso, cabe dejar un campo libre en las ListViews de ingredientes y pasos
                ingredientListAdapter.addIngredientStep("");
                stepListAdapter.addIngredientStep("");
            }
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //Activamos el botón de Atrás en la barra superior
        actionBar.setTitle(editingRecipe ? EDIT_RECIPE_ACTIVITY_TITLE : NEW_RECIPE_ACTIVITY_TITLE); //Cambiamos el título en función de si estamos editando o no
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        Recipe recipe = presenter.getCurrentRecipe(); //Primeramente necesitamos que se genere la receta actual (lo acaba haciendo el model)

        savedInstanceState.putParcelable("CurrentRecipe", recipe); //Guardamos la receta
        savedInstanceState.putInt("CurrentRecipeID", currentRecipeID); //El ID actual
        savedInstanceState.putStringArrayList("IngredientList", getIngredientList()); //La lista de ingredientes
        savedInstanceState.putStringArrayList("StepList", getStepList()); //La lista de pasos
        savedInstanceState.putBoolean("Editing", editingRecipe); //Si se está editando o no
    }

    //Este método se llama al interactuar con alguno de los elementos de la Action Bar, pero únicamente está activado el botón de Atrás
    public boolean onOptionsItemSelected(MenuItem item) {

        //Primero el Dialog
        //finish(); //Terminamos la actividad, por lo que volvemos a la anterior actividad
        //exitFromActivity();
        //finish();
        showReturnDialog();
        return true;
    }

    public void onBackPressed() {
        showReturnDialog();
    }

    // Listeners

    //Método que se llama cuando se pulsa el botón de añadir nuevo ingrediente a la ListView
    public void addNewIngredient(View view) {

        ingredientListAdapter.addIngredientStep("");
    }

    //Método que se llama cuando se pulsa el botón de añadir nuevo paso a la ListView
    public void addNewStep(View view) {

        stepListAdapter.addIngredientStep("");
    }

    //Método que se ejecuta cuando el Listener asignado a la SeekBar detecta un cambio en su valor
    public void seekBarUpdated() {

        //Cambia el texto de información sobre el número de comensales para indique en texto el número actual
        numGuestsText.setText(NUMBER_GUESTS_BASE_TEXT + numGuestsSeekBar.getProgress());
    }

    //Método que se llama cuando se pulsa el botón Finish
    public void finishRecipe(View view) {

        //Primeramente se llama al método checkFilledFields para comprobar si todos los campos obligatorios están rellenados
        if(checkFilledFields())
        {
            //En caso afirmativo, se obtiene la receta
            Recipe recipe = presenter.getCurrentRecipe();
            //Se guarda en el campo de id de la receta el valor para el id actual
            recipe.id = currentRecipeID;
            //Se le indica al presenter que ya puede crear la receta, es decir, guardarla en la base de datos
            presenter.createRecipe(recipe, getIngredientList(), getStepList());

            //Se le indica al usuario que la receta se va a guardar
            showToast(recipeSaved);
            //showToast(recipeSaved);
            showDialog();
        }

    }


    // Getters y setters

    //Métodos para obtener los valores de los elementos de la view
    public String getRecipeName()
    {
        return String.valueOf(recipeName.getText());
    }
    public int getRecipeTypeID()
    {
        return recipeType.getSelectedItemPosition();
    }
    public int getNumGuests()
    {
        return numGuestsSeekBar.getProgress();
    }
    public float getValuation()
    {
        return valuation.getRating();
    }
    public int getDifficultyID()
    {
        return difficulty.getSelectedItemPosition();
    }
    public ArrayList<String> getIngredientList() {

        return ingredientListAdapter.getIngredientStepList();
    }
    public ArrayList<String> getStepList() {

        return stepListAdapter.getIngredientStepList();
    }

    //Métodos para asignar a los elementos de la view un nuevo valor
    public void setRecipeName(String value) { recipeName.setText(value); }
    public void setRecipeTypeID(int value) { recipeType.setSelection(value); }
    public void setNumGuests(int value) { numGuestsSeekBar.setProgress(value); }
    public void setValuation(float value) { valuation.setRating(value); }
    public void setDifficultyID(int value) { difficulty.setSelection(value); }
    public void setIngredientList(ArrayList<String> value) {

        ingredientListAdapter.setIngredientStepList(value);
    }
    public void setStepList(ArrayList<String> value) {

        stepListAdapter.setIngredientStepList(value);
    }

    //Método que asigna a los campos los valores correspondientes
    public void setRecipe(Recipe recipe) {

        setRecipeName(recipe.name);
        setRecipeTypeID(recipe.typeID);
        setNumGuests(recipe.guests);
        setValuation(recipe.valuation);
        setDifficultyID(recipe.difficultyID);
    }


    // Otros métodos

    //Método que se llamará desde el presenter cuando la receta se haya guardado en la base de datos
    public void exitFromActivity() {

        //Vuelve a la actividad anterior
        finish();
    }

    //Método útil para comprobar los campos obligatorios están completados para poder guardar la receta
    public boolean checkFilledFields() {

        if(recipeName.getText().toString().length() == 0) //Existe un nombre
        {
            showToast(requiresRecipeName); //Si no, se le indica al usuario
            return false;
        }
        else if(((IngredientStepListAdapter) ingredientList.getAdapter()).checkFilledIngredientsSteps()) //Todos los campos de ingredientes están completos
        {
            showToast(requiresIngredients); //Si no, se le indica al usuario
            return false;
        }
        else if(((IngredientStepListAdapter) stepList.getAdapter()).checkFilledIngredientsSteps()) //Todos los campos de pasos están completos
        {
            showToast(requiresSteps); //Si no, se le indica al usuario
            return false;
        }
        return true; //Si t odo es correcto, se devuelve un true
    }

    //Método que es llamado desde la clase IngredientStepListAdapter para modificar la altura de una ListView en función de su número de elementos,
    //para cuando este se ve modificado
    public void changeHeightList(ListView list) {

        IngredientStepListAdapter adapter = (IngredientStepListAdapter) list.getAdapter();

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, list);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = list.getLayoutParams();
        params.height = totalHeight + (list.getDividerHeight() * (adapter.getCount() - 1));
        list.setLayoutParams(params);
        list.requestLayout();
    }

    //Método para mostrar por pantalla información mediante un Toast
    public void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showDialog()
    {
        EditRecipeDialog dialog = new EditRecipeDialog(this);
        dialog.show(getSupportFragmentManager(), "my_dialog");
    }

    public void showReturnDialog() {
        ReturnRecipeDialog dialog = new ReturnRecipeDialog(this);
        dialog.show(getSupportFragmentManager(), "my_dialog");
    }
}
