package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;

import com.android.volley.Response;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.Model;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;

/*
    Presenter de MyRecipesListActivity
 */

public class MyRecipesListPresenter
{
    MyRecipesListActivity view;
    Model model;

    public MyRecipesListPresenter(MyRecipesListActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    //Método que solicitará al model una lista de objetos que contendrá una array de recetas, una array de integers que se corresponden con el número de ingredientes de cada
    //receta y una array de integers que se corresponden con el número de pasos de cada receta
    public void requestRecipeList(int recipeType) {
        model.getRecipeList(new Response.Listener<ArrayList<Object>>() {
            @Override
            public void onResponse(ArrayList<Object> response) {
                setRecipes(response);
            }
        }, recipeType);
    }

    //Método que envía a la view el resultado de la solicitud de información al model
    public void setRecipes(ArrayList<Object> recipeIngredientsSteps)
    {
        view.setRecipeList((Recipe[]) recipeIngredientsSteps.get(0), (int[]) recipeIngredientsSteps.get(1), (int[]) recipeIngredientsSteps.get(2));
    }

    //Método que solicita al model de la eliminación de cierta receta
    public void deleteRecipe(Recipe recipe) {

        model.deleteRecipe(recipe, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                //Progress bar
            }
        });
    }

    //Método que solicita al model el nombre de un tipo de receta a partir de su ID
    public String getRecipeTypeName(int typeID)
    {
        return model.getRecipeTypeName(typeID);
    }

    //Método que solicita al model el nombre de una dificultad a partir de su ID
    public String getDifficultyName(int difficultyID)
    {
        return model.getDifficultyName(difficultyID);
    }
}
