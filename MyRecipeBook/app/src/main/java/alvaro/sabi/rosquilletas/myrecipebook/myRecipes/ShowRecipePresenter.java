package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;

import com.android.volley.Response;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.Model;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Ingredient;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.StepToFollow;

public class ShowRecipePresenter {

    private ShowRecipeActivity view;
    private Model model;

    public ShowRecipePresenter(ShowRecipeActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    public void requestIngredientList(int recipeID)
    {
        model.getIngredientListFromRecipe(recipeID, new Response.Listener<Ingredient[]>() {
            @Override
            public void onResponse(Ingredient[] response) {
                setIngredientList(response);
            }
        });
    }

    public void requestStepList(int recipeID)
    {
        model.getStepListFromRecipe(recipeID, new Response.Listener<StepToFollow[]>() {
            @Override
            public void onResponse(StepToFollow[] response) {
                setStepList(response);
            }
        });
    }

    public void setIngredientList(Ingredient[] ingredientList)
    {
        ArrayList<String> ingredientNameList = new ArrayList<>();

        for(int i = 0; i < ingredientList.length; i++)
        {
            ingredientNameList.add(ingredientList[i].name);
        }

        view.setIngredientList(ingredientNameList);
    }

    public void setStepList(StepToFollow[] stepList)
    {
        ArrayList<String> stepNameList = new ArrayList<>();

        for(int i = 0; i < stepList.length; i++)
        {
            stepNameList.add(stepList[i].description);
        }

        view.setStepList(stepNameList);
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
