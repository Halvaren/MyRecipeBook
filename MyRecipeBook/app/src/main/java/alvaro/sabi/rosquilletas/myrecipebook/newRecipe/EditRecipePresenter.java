package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.Model;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Ingredient;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.StepToFollow;

public class EditRecipePresenter {

    private EditRecipeActivity view;
    private Model model;

    public EditRecipePresenter(EditRecipeActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    public String[] getRecipeTypeNames(){
        return model.getRecipeTypeNames();
    }

    public String[] getDifficultyNames() { return model.getDifficultyNames(); }

    public void getRecipeByID(int currentRecipeID) { model.getRecipeByID(currentRecipeID, new Response.Listener<Recipe>() {
        @Override
        public void onResponse(Recipe response) {
            setRecipe(response);
        }
    }); }

    public Recipe getCurrentRecipe() { return model.getCurrentRecipe(this); }

    public void getIngredientListFromRecipe(int currentRecipeID) {
        model.getIngredientListFromRecipe(currentRecipeID, new Response.Listener<Ingredient[]>() {
            @Override
            public void onResponse(Ingredient[] response) {
                setIngredientListFromRecipe(response);
            }
        });
    }

    public void getStepListFromRecipe(int currentRecipeID) {
        model.getStepListFromRecipe(currentRecipeID, new Response.Listener<StepToFollow[]>() {
            @Override
            public void onResponse(StepToFollow[] response) {
                setStepListFromRecipe(response);
            }
        });
    }

    public void setRecipe(Recipe result)
    {
        view.setRecipe(result);
    }

    public void setIngredientListFromRecipe(Ingredient[] result)
    {
        ArrayList<String> ingredientNames = new ArrayList<>();
        for(int i = 0; i < result.length; i++)
        {
            ingredientNames.add(result[i].name);
        }

        view.setIngredientList(ingredientNames);
    }

    public void setStepListFromRecipe(StepToFollow[] result)
    {
        ArrayList<String> stepNames = new ArrayList<>();
        for(int i = 0; i < result.length; i++)
        {
            stepNames.add(result[i].description);
        }

        view.setStepList(stepNames);
    }

    public void createRecipe(Recipe recipe, ArrayList<String> ingredients, ArrayList<String> steps)
    {
        model.createRecipe(recipe, ingredients, steps, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                //Progress bar
            }
        });
    }

    public String getRecipeName() { return view.getRecipeName(); }
    public int getRecipeTypeID() { return view.getRecipeTypeID(); }
    public int getNumGuests() { return view.getNumGuests(); }
    public float getValuation() { return view.getValuation(); }
    public int getDifficultyID() { return view.getDifficultyID(); }
}
