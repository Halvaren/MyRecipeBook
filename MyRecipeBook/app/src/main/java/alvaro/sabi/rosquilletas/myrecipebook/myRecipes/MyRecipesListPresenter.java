package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

import alvaro.sabi.rosquilletas.myrecipebook.Model;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;

public class MyRecipesListPresenter
{
    MyRecipesListActivity view;
    Model model;

    public MyRecipesListPresenter(MyRecipesListActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    public void requestRecipeList(int recipeType) {
        model.getRecipeList(new Response.Listener<ArrayList<Object>>() {
            @Override
            public void onResponse(ArrayList<Object> response) {
                setRecipes(response);
            }
        }, recipeType);
    }

    public void setRecipes(ArrayList<Object> recipeIngredientsSteps)
    {
        view.setRecipeList((Recipe[]) recipeIngredientsSteps.get(0), (int[]) recipeIngredientsSteps.get(1), (int[]) recipeIngredientsSteps.get(2));
    }

    public void deleteRecipe(Recipe recipe) {
        model.deleteRecipe(recipe, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                //Progress bar
                //view.updateRecipeList();
            }
        });
    }

    public String getRecipeTypeName(int typeID)
    {
        return model.getRecipeTypeName(typeID);
    }

    public String getDifficultyName(int difficultyID)
    {
        return model.getDifficultyName(difficultyID);
    }
}
