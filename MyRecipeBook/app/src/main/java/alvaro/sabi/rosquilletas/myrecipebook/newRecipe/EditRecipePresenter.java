package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.content.Context;

import com.android.volley.Response;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.Model;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;

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

    public Recipe getCurrentRecipe() { return model.getCurrentRecipe(this); }

    public void setCurrentRecipe(Recipe currentRecipe) { model.setCurrentRecipe(this, currentRecipe); }

    public void createRecipe(Recipe recipe)
    {
        model.createRecipe(recipe, new Response.Listener<Void>() {
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
    public ArrayList<String> getIngredientList() { return view.getIngredientList(); }
    public ArrayList<String> getStepList() { return view.getStepList(); }

    public void setRecipeName(String value) { view.setRecipeName(value); }
    public void setRecipeTypeID(int value) { view.setRecipeTypeID(value); }
    public void setNumGuests(int value) { view.setNumGuests(value); }
    public void setValuation(float value) { view.setValuation(value); }
    public void setDifficultyID(int value) { view.setDifficultyID(value); }
    public void setIngredientList(ArrayList<String> value) { view.setIngredientList(value); }
    public void setStepList(ArrayList<String> value) { view.setStepList(value); }
}
