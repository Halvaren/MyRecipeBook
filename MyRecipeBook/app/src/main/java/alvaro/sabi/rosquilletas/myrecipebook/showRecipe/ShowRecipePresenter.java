package alvaro.sabi.rosquilletas.myrecipebook.showRecipe;

import android.content.Context;

import com.android.volley.Response;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.Model;
import alvaro.sabi.rosquilletas.myrecipebook.database.Ingredient;
import alvaro.sabi.rosquilletas.myrecipebook.database.StepToFollow;

/*
    Presenter de ShowRecipeActivity
 */

public class ShowRecipePresenter {

    private ShowRecipeActivity view;
    private Model model;

    public ShowRecipePresenter(ShowRecipeActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    //Método que solicita al model la lista de ingredientes de una receta a partir de su ID
    public void requestIngredientList(int recipeID)
    {
        model.getIngredientListFromRecipe(recipeID, new Response.Listener<Ingredient[]>() {
            @Override
            public void onResponse(Ingredient[] response) {
                setIngredientList(response);
            }
        });
    }

    //Método que solicita al model la lista de pasos a seguir de una receta a partir de su ID
    public void requestStepList(int recipeID)
    {
        model.getStepListFromRecipe(recipeID, new Response.Listener<StepToFollow[]>() {
            @Override
            public void onResponse(StepToFollow[] response) {
                setStepList(response);
            }
        });
    }

    //Método que toma una lista de ingredientes, sustrae sus nombres y los devuelve a la view
    public void setIngredientList(Ingredient[] ingredientList)
    {
        ArrayList<String> ingredientNameList = new ArrayList<>();

        for(int i = 0; i < ingredientList.length; i++)
        {
            ingredientNameList.add(ingredientList[i].name);
        }

        view.setIngredientList(ingredientNameList);
    }

    //Método que toma una lista de pasos, sustrae sus descripciones y las devuelve a la view
    public void setStepList(StepToFollow[] stepList)
    {
        ArrayList<String> stepNameList = new ArrayList<>();

        for(int i = 0; i < stepList.length; i++)
        {
            stepNameList.add(stepList[i].description);
        }

        view.setStepList(stepNameList);
    }

    //Método que solicita al model y devuelve el nombre de un tipo de receta a partir de su ID
    public String getRecipeTypeName(int typeID)
    {
        return model.getRecipeTypeName(typeID);
    }

    //Método que solicita al model y devuelve el nombre de una dificiltad a partir de su ID
    public String getDifficultyName(int difficultyID)
    {
        return model.getDifficultyName(difficultyID);
    }
}
