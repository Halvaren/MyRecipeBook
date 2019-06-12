package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.MainActivity;
import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.IngredientStepListAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class EditRecipeActivity extends AppCompatActivity {

    private final String NUMBER_GUESTS_BASE_TEXT = "Number of guests: ";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe_activity);

        presenter = new EditRecipePresenter(this, this);

        recipeName = findViewById(R.id.editRecipeNameField);
        recipeType = findViewById(R.id.editRecipeTypeSpinner);
        numGuestsText = findViewById(R.id.editRecipeNGuestsText);
        numGuestsSeekBar = findViewById(R.id.editRecipeNGuestsSeekBar);

        addIngredientButton = findViewById(R.id.editRecipeNewIngredientButton);
        addStepButton = findViewById(R.id.editRecipeNewStepButton);
        ingredientList = findViewById(R.id.editRecipeIngredientList);
        stepList = findViewById(R.id.editRecipeStepList);

        IngredientStepListAdapter ingredientAdapter = new IngredientStepListAdapter(this, this, ingredientList);
        ingredientList.setAdapter(ingredientAdapter);

        IngredientStepListAdapter stepAdapter = new IngredientStepListAdapter(this, this, stepList);
        stepList.setAdapter(stepAdapter);

        valuation = findViewById(R.id.editRecipeRatingBar);
        difficulty = findViewById(R.id.editRecipeDifficultySpinner);

        ArrayAdapter<String> recipeTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presenter.getRecipeTypeNames());
        recipeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeType.setAdapter(recipeTypeAdapter);

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presenter.getDifficultyNames());
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultyAdapter);

        Intent intent = getIntent();
        Recipe currentRecipe = intent.getParcelableExtra("EditRecipe");


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

        if(savedInstanceState != null)
        {
            Recipe recipe = savedInstanceState.getParcelable("CurrentRecipe");
            presenter.setCurrentRecipe(recipe);
        }
        else
        {
            if(currentRecipe != null){

                Recipe recipe = savedInstanceState.getParcelable("EditRecipe");
                presenter.setCurrentRecipe(recipe);
            }
            else {
                ingredientAdapter.addIngredientStep("");
                stepAdapter.addIngredientStep("");
            }


        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable("CurrentRecipe", presenter.getCurrentRecipe());
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
            presenter.createRecipe(recipe);

            Intent intent = new Intent(EditRecipeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public boolean checkFilledFields()
    {
        if(recipeName.getText().toString().length() == 0)
        {
            Log.d("hola", "hola");
            return false;
        }
        else if(((IngredientStepListAdapter) ingredientList.getAdapter()).checkFilledIngredientsSteps())
        {
            Log.d("hola", "adios");
            return false;
        }
        else if(((IngredientStepListAdapter) stepList.getAdapter()).checkFilledIngredientsSteps())
        {
            Log.d("hola", "ard");
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
        return ((IngredientStepListAdapter) ingredientList.getAdapter()).getIngredientStepList();
    }
    public ArrayList<String> getStepList() {
        return ((IngredientStepListAdapter) stepList.getAdapter()).getIngredientStepList();
    }

    public void setRecipeName(String value) { recipeName.setText(value); }
    public void setRecipeTypeID(int value) { recipeType.setSelection(value); }
    public void setNumGuests(int value) { numGuestsSeekBar.setProgress(value); }
    public void setValuation(float value) { valuation.setRating(value); }
    public void setDifficultyID(int value) { difficulty.setSelection(value); }
    public void setIngredientList(ArrayList<String> value) {
        ((IngredientStepListAdapter) ingredientList.getAdapter()).setIngredientStepList(value);
    }
    public void setStepList(ArrayList<String> value) {
        ((IngredientStepListAdapter) stepList.getAdapter()).setIngredientStepList(value);
    }
}
