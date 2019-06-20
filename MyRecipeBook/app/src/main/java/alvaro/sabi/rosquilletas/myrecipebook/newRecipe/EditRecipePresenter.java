package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.content.Context;

import com.android.volley.Response;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.Model;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Ingredient;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.StepToFollow;

/*
    Presenter de EditRecipeActivity
 */

public class EditRecipePresenter {

    private EditRecipeActivity view;
    private Model model;

    public EditRecipePresenter(EditRecipeActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    //Método que solicita al model y devuelve la lista de nombres de tipos de recetas
    public String[] getRecipeTypeNames(){
        return model.getRecipeTypeNames();
    }

    //Método que solicita al model y devuelve la lista de nombres de dificultades
    public String[] getDifficultyNames() { return model.getDifficultyNames(); }

    //Método que solicita al model que prepare una receta a partir de una ID
    public void getRecipeByID(int currentRecipeID) {
        model.getRecipeByID(currentRecipeID, new Response.Listener<Recipe>() {
            @Override
            public void onResponse(Recipe response) {
                setRecipe(response);
            }
        });
    }

    //Método que devuele a la view la receta solicitada
    public void setRecipe(Recipe result)
    {
        view.setRecipe(result);
    }

    //Método que solicita al model que genere la receta actual y la devuelve
    public Recipe getCurrentRecipe() { return model.getCurrentRecipe(this); }

    //Método que solicita al model que prepare la lista de ingredientes de una receta a partir de su ID
    public void getIngredientListFromRecipe(int currentRecipeID) {
        model.getIngredientListFromRecipe(currentRecipeID, new Response.Listener<Ingredient[]>() {
            @Override
            public void onResponse(Ingredient[] response) {
                setIngredientListFromRecipe(response);
            }
        });
    }

    //Método que solicita al model que prepare la lista de pasos de una receta a partir de su ID
    public void getStepListFromRecipe(int currentRecipeID) {
        model.getStepListFromRecipe(currentRecipeID, new Response.Listener<StepToFollow[]>() {
            @Override
            public void onResponse(StepToFollow[] response) {
                setStepListFromRecipe(response);
            }
        });
    }

    //Método que, a partir de la lista de ingredientes previamente solicitada, extrae sus nombres y los devuelve a la view
    public void setIngredientListFromRecipe(Ingredient[] result)
    {
        ArrayList<String> ingredientNames = new ArrayList<>();
        for(int i = 0; i < result.length; i++)
        {
            ingredientNames.add(result[i].name);
        }

        view.setIngredientList(ingredientNames);
    }

    //Método que, a partir de la lista de pasos previamente solicitada, extrae sus descripciones y los devuelve a la view
    public void setStepListFromRecipe(StepToFollow[] result)
    {
        ArrayList<String> stepNames = new ArrayList<>();
        for(int i = 0; i < result.length; i++)
        {
            stepNames.add(result[i].description);
        }

        view.setStepList(stepNames);
    }

    //Método que solicita al model la creación de una receta a partir de la misma, la lista de nombres de sus ingredientes y la lista de descripciones de sus pasos
    public void createRecipe(Recipe recipe, ArrayList<String> ingredients, ArrayList<String> steps)
    {
       model.createRecipe(recipe, ingredients, steps, new Response.Listener<Void>() {
           @Override
            public void onResponse(Void response) {
                //exitFromActivity();
            }
        });
    }

    //Método que solicita a la view el fin de la actividad actual
    public void exitFromActivity()
    {
        view.exitFromActivity();
    }

    //Getters
    public String getRecipeName() { return view.getRecipeName(); }
    public int getRecipeTypeID() { return view.getRecipeTypeID(); }
    public int getNumGuests() { return view.getNumGuests(); }
    public float getValuation() { return view.getValuation(); }
    public int getDifficultyID() { return view.getDifficultyID(); }
}
