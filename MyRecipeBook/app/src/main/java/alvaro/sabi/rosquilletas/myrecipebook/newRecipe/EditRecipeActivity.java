package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import alvaro.sabi.rosquilletas.myrecipebook.MainActivity;
import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.ToastMessages;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.IngredientStepListAdapter;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.MyRecipesListActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EditRecipeActivity extends AppCompatActivity implements ToastMessages {

    public final String NEW_RECIPE_ACTIVITY_TITLE = "New Recipe";
    public final String EDIT_RECIPE_ACTIVITY_TITLE = "Edit Recipe";
    public final String NUMBER_GUESTS_BASE_TEXT = "Number of guests: ";

    private EditRecipePresenter presenter;

    private EditText recipeName;
    private Spinner recipeType;
    private TextView numGuestsText;
    private SeekBar numGuestsSeekBar;

    private Button addIngredientButton;
    private ListView ingredientList;

    private Button addStepButton;
    private ListView stepList;

    private RatingBar valuation;
    private Spinner difficulty;

    private IngredientStepListAdapter ingredientListAdapter;
    private IngredientStepListAdapter stepListAdapter;

    private int currentRecipeID;

    private boolean editingRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe_activity);

        currentRecipeID = -1;

        presenter = new EditRecipePresenter(this, this);

        recipeName = findViewById(R.id.editRecipeNameField);
        recipeType = findViewById(R.id.editRecipeTypeSpinner);
        numGuestsText = findViewById(R.id.editRecipeNGuestsText);
        numGuestsSeekBar = findViewById(R.id.editRecipeNGuestsSeekBar);

        addIngredientButton = findViewById(R.id.editRecipeNewIngredientButton);
        addStepButton = findViewById(R.id.editRecipeNewStepButton);
        ingredientList = findViewById(R.id.editRecipeIngredientList);
        stepList = findViewById(R.id.editRecipeStepList);

        ingredientListAdapter = new IngredientStepListAdapter(this, this, ingredientList);
        ingredientList.setAdapter(ingredientListAdapter);

        stepListAdapter = new IngredientStepListAdapter(this, this, stepList);
        stepList.setAdapter(stepListAdapter);

        valuation = findViewById(R.id.editRecipeRatingBar);
        difficulty = findViewById(R.id.editRecipeDifficultySpinner);

        ArrayAdapter<String> recipeTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presenter.getRecipeTypeNames());
        recipeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeType.setAdapter(recipeTypeAdapter);

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presenter.getDifficultyNames());
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultyAdapter);

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

        if(savedInstanceState != null)
        {
            Recipe recipe = savedInstanceState.getParcelable("CurrentRecipe");
            currentRecipeID = savedInstanceState.getInt("CurrentRecipeID");
            setRecipe(recipe);

            setIngredientList(savedInstanceState.getStringArrayList("IngredientList"));
            setStepList(savedInstanceState.getStringArrayList("StepList"));

            editingRecipe = savedInstanceState.getBoolean("Editing");
        }
        else
        {
            Intent intent = getIntent();
            currentRecipeID = intent.getIntExtra("EditRecipeID", -1);
            if(currentRecipeID != -1)
            {
                presenter.getRecipeByID(currentRecipeID);
                presenter.getIngredientListFromRecipe(currentRecipeID);
                presenter.getStepListFromRecipe(currentRecipeID);

                editingRecipe = true;
            }
            else
            {
                ingredientListAdapter.addIngredientStep("");
                stepListAdapter.addIngredientStep("");
            }
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(editingRecipe ? EDIT_RECIPE_ACTIVITY_TITLE : NEW_RECIPE_ACTIVITY_TITLE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        Recipe recipe = presenter.getCurrentRecipe();

        savedInstanceState.putParcelable("CurrentRecipe", recipe);
        savedInstanceState.putInt("CurrentRecipeID", currentRecipeID);
        savedInstanceState.putStringArrayList("IngredientList", getIngredientList());
        savedInstanceState.putStringArrayList("StepList", getStepList());
        savedInstanceState.putBoolean("Editing", editingRecipe);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        //Primero el Dialog
        //exitFromActivity();
        finish();
        return true;
    }

    public void setRecipe(Recipe recipe)
    {
        setRecipeName(recipe.name);
        setRecipeTypeID(recipe.typeID);
        setNumGuests(recipe.guests);
        setValuation(recipe.valuation);
        setDifficultyID(recipe.difficultyID);
    }

    public void addNewIngredient(View view)
    {
        IngredientStepListAdapter adapter = (IngredientStepListAdapter) ingredientList.getAdapter();
        adapter.addIngredientStep("");
    }

    public void addNewStep(View view)
    {
        IngredientStepListAdapter adapter = (IngredientStepListAdapter) stepList.getAdapter();
        adapter.addIngredientStep("");
    }

    public void changeHeightList(ListView list)
    {
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

    public void seekBarUpdated()
    {
        numGuestsText.setText(NUMBER_GUESTS_BASE_TEXT + numGuestsSeekBar.getProgress());
    }

    public void finishRecipe(View view)
    {
        if(checkFilledFields())
        {
            Recipe recipe = presenter.getCurrentRecipe();
            recipe.id = currentRecipeID;
            presenter.createRecipe(recipe, getIngredientList(), getStepList());

            showToast(recipeSaved);
        }
    }

    public void exitFromActivity()
    {
        if(editingRecipe)
        {
            Intent intent = new Intent(EditRecipeActivity.this, MyRecipesListActivity.class);
            intent.putExtra("RecipeType", presenter.getCurrentRecipe().typeID);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(EditRecipeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public boolean checkFilledFields()
    {
        if(recipeName.getText().toString().length() == 0)
        {
            showToast(requiresRecipeName);
            return false;
        }
        else if(((IngredientStepListAdapter) ingredientList.getAdapter()).checkFilledIngredientsSteps())
        {
            showToast(requiresIngredients);
            return false;
        }
        else if(((IngredientStepListAdapter) stepList.getAdapter()).checkFilledIngredientsSteps())
        {
            showToast(requiresSteps);
            return false;
        }
        return true;
    }

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

    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
